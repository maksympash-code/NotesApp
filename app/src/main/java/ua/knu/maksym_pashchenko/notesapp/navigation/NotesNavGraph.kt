package ua.knu.maksym_pashchenko.notesapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import ua.knu.maksym_pashchenko.notesapp.data.local.database.NotesDatabase
import ua.knu.maksym_pashchenko.notesapp.data.repository.NotesRepositoryImpl
import ua.knu.maksym_pashchenko.notesapp.presentation.details.NoteDetailsScreen
import ua.knu.maksym_pashchenko.notesapp.presentation.details.NoteDetailsViewModel
import ua.knu.maksym_pashchenko.notesapp.presentation.details.NoteDetailsViewModelFactory
import ua.knu.maksym_pashchenko.notesapp.presentation.edit.NoteEditScreen
import ua.knu.maksym_pashchenko.notesapp.presentation.edit.NoteEditViewModel
import ua.knu.maksym_pashchenko.notesapp.presentation.edit.NoteEditViewModelFactory
import ua.knu.maksym_pashchenko.notesapp.presentation.notes.view_model.NotesListViewModel
import ua.knu.maksym_pashchenko.notesapp.presentation.notes.view_model.NotesListViewModelFactory
import ua.knu.maksym_pashchenko.notesapp.presentation.notes.NotesScreen

@Composable
fun NotesNavGraph() {
    val navController = rememberNavController()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current.applicationContext

    val repository = remember {
        val database = NotesDatabase.getDatabase(context)
        NotesRepositoryImpl(database.noteDao())
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.NOTES_LIST,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Routes.NOTES_LIST) {
                val notesListViewModel: NotesListViewModel = viewModel(
                    factory = NotesListViewModelFactory(repository)
                )

                val uiState by notesListViewModel.uiState.collectAsStateWithLifecycle()

                NotesScreen(
                    uiState = uiState,
                    onSortTypeChanged = notesListViewModel::onSortTypeChanged,
                    onSearchQueryChanged = notesListViewModel::onSearchQueryChanged,
                    onNoteClick = { noteId ->
                        navController.navigate(Routes.noteDetails(noteId))
                    },
                    onAddNoteClick = {
                        navController.navigate(Routes.noteEdit())
                    }
                )
            }

            composable(
                route = Routes.NOTE_DETAILS_ROUTE,
                arguments = listOf(
                    navArgument(Routes.NOTE_ID_ARGUMENT) {
                        type = NavType.LongType
                    }
                )
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getLong(Routes.NOTE_ID_ARGUMENT)

                if (noteId != null) {

                    val noteDetailsViewModel: NoteDetailsViewModel = viewModel(
                        factory = NoteDetailsViewModelFactory(repository)
                    )

                    LaunchedEffect(noteId) {
                        noteDetailsViewModel.loadNote(noteId)
                    }

                    val uiState = noteDetailsViewModel.uiState.collectAsStateWithLifecycle()

                    NoteDetailsScreen(
                        uiState = uiState.value,
                        onBack = {
                            navController.popBackStack()
                        },
                        onEditClick = { editNoteId ->
                            navController.navigate(Routes.noteEdit(editNoteId))
                        },
                        onDeleteConfirm = {
                            noteDetailsViewModel.deleteNote(
                                onDeleted = { deletedNote ->
                                    navController.popBackStack()

                                    coroutineScope.launch {
                                        val result = snackbarHostState.showSnackbar(
                                            message = "Note deleted",
                                            actionLabel = "Undo",
                                            duration = SnackbarDuration.Long
                                        )

                                        if (result == SnackbarResult.ActionPerformed) {
                                            repository.insertNote(deletedNote)
                                        }
                                    }
                                }
                            )
                        }
                    )
                }
            }

            composable(
                route = Routes.NOTE_EDIT_ROUTE,
                arguments = listOf(
                    navArgument(Routes.NOTE_ID_ARGUMENT) {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                )
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments
                    ?.getString(Routes.NOTE_ID_ARGUMENT)
                    ?.toLongOrNull()

                val noteEditViewModel: NoteEditViewModel = viewModel(
                    factory = NoteEditViewModelFactory(repository)
                )

                LaunchedEffect(noteId) {
                    noteEditViewModel.loadNote(noteId)
                }

                val uiState = noteEditViewModel.uiState.collectAsStateWithLifecycle()

                NoteEditScreen(
                    noteId = noteId,
                    uiState = uiState.value,
                    onTitleChange = noteEditViewModel::onTitleChange,
                    onContentChange = noteEditViewModel::onContentChange,
                    onSaveClick = {
                        noteEditViewModel.saveNote(
                            onSaved = {
                                navController.popBackStack()
                            }
                        )
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }

}
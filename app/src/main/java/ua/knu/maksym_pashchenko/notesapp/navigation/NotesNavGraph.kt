package ua.knu.maksym_pashchenko.notesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ua.knu.maksym_pashchenko.notesapp.data.local.database.NotesDatabase
import ua.knu.maksym_pashchenko.notesapp.data.repository.NotesRepositoryImpl
import ua.knu.maksym_pashchenko.notesapp.presentation.details.NoteDetailsScreen
import ua.knu.maksym_pashchenko.notesapp.presentation.edit.NoteEditScreen
import ua.knu.maksym_pashchenko.notesapp.presentation.notes.NotesListViewModel
import ua.knu.maksym_pashchenko.notesapp.presentation.notes.NotesListViewModelFactory
import ua.knu.maksym_pashchenko.notesapp.presentation.notes.NotesScreen

@Composable
fun NotesNavGraph() {
    val navController = rememberNavController()

    val context = LocalContext.current.applicationContext

    val repository = remember {
        val database = NotesDatabase.getDatabase(context)
        NotesRepositoryImpl(database.noteDao())
    }

    NavHost (
        navController = navController,
        startDestination = Routes.NOTES_LIST
    ) {
        composable(route = Routes.NOTES_LIST) {
            val notesListViewModel: NotesListViewModel = viewModel(
                factory = NotesListViewModelFactory(repository)
            )

            val notes = notesListViewModel.notes.collectAsStateWithLifecycle()

            NotesScreen(
                notes = notes.value,
                onNoteClick = { noteId ->
                    navController.navigate(Routes.noteDetails(noteId))
                },
                onTestNoteClick = {
                    notesListViewModel.addTestNote()
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
                NoteDetailsScreen(
                    noteId = noteId,
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = { editNoteId ->
                        navController.navigate(Routes.noteEdit(editNoteId))
                    },
                    onDeleteClick = {
                        navController.popBackStack()
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

            NoteEditScreen(
                noteId = noteId
            )
        }
    }
}
package ua.knu.maksym_pashchenko.notesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ua.knu.maksym_pashchenko.notesapp.presentation.details.NoteDetailsScreen
import ua.knu.maksym_pashchenko.notesapp.presentation.edit.NoteEditScreen
import ua.knu.maksym_pashchenko.notesapp.presentation.notes.NotesScreen

@Composable
fun NotesNavGraph() {
    val navController = rememberNavController()

    NavHost (
        navController = navController,
        startDestination = Routes.NOTES_LIST
    ) {
        composable(route = Routes.NOTES_LIST) {
            NotesScreen(
                onNoteClick = { noteId ->
                    navController.navigate(Routes.noteDetails(noteId))
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
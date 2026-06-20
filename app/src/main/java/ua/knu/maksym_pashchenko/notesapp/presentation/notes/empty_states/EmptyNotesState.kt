package ua.knu.maksym_pashchenko.notesapp.presentation.notes.empty_states

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EmptyNotesState(
    modifier: Modifier = Modifier
) {
    EmptyStateView(
        icon = "📝",
        title = "There are no notes yet",
        description = "Tap + to create your first one",
        modifier = modifier
    )
}
package ua.knu.maksym_pashchenko.notesapp.presentation.notes.empty_states

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EmptySearchState(
    query: String,
    modifier: Modifier = Modifier
) {
    EmptyStateView(
        icon = "🔍",
        title = "Nothing found",
        description = "There are no notes for the query “$query”",
        modifier = modifier
    )
}
package ua.knu.maksym_pashchenko.notesapp.presentation.notes.elements

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.knu.maksym_pashchenko.notesapp.presentation.notes.NotesSortType

@Composable
fun SortMenu(
    selectedSortType: NotesSortType,
    onSortTypeChanged: (NotesSortType) -> Unit
) {
    Text(
        text = "Sort",
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier.padding(top = 12.dp)
    )

    Row(
        modifier = Modifier.padding(top = 8.dp)
    ) {
        FilterChip(
            selected = selectedSortType == NotesSortType.DATE_DESC,
            onClick = {
                onSortTypeChanged(NotesSortType.DATE_DESC)
            },
            label = {
                Text(
                    text = "Newest first"
                )
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        FilterChip(
            selected = selectedSortType == NotesSortType.DATE_ASC,
            onClick = {
                onSortTypeChanged(NotesSortType.DATE_ASC)
            },
            label = {
                Text(
                    text = "Oldest first"
                )
            }
        )

    }

    Row(
        modifier = Modifier.padding(top = 8.dp)
    ) {
        FilterChip(
            selected = selectedSortType == NotesSortType.TITLE_ASC,
            onClick = {
                onSortTypeChanged(NotesSortType.TITLE_ASC)
            },
            label = {
                Text(
                    text = "A -> Z"
                )
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        FilterChip(
            selected = selectedSortType == NotesSortType.TITLE_DESC,
            onClick = {
                onSortTypeChanged(NotesSortType.TITLE_DESC)
            },
            label = {
                Text(
                    text = "Z -> A"
                )
            }
        )
    }
}
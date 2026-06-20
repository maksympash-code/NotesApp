package ua.knu.maksym_pashchenko.notesapp.presentation.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.knu.maksym_pashchenko.notesapp.domain.model.Note

@Composable
fun NotesScreen(
    notes: List<Note>,
    searchQuery: String,
    sortType: NotesSortType,
    onSortTypeChanged: (NotesSortType) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onNoteClick: (Long) -> Unit,
    onCreateNoteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Notes",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = onCreateNoteClick,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Create note")
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            singleLine = true,
            label = {
                Text("Search")
            },
            placeholder = {
                Text("Search by title or content")
            }
        )

        Text(
            text = "Sort by date",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = 12.dp)
        )

        Row(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            FilterChip(
                selected = sortType == NotesSortType.DATE_DESC,
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
                selected = sortType == NotesSortType.DATE_ASC,
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

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = notes,
                key = { note -> note.id }
            ) { note ->
                NoteCard(
                    note = note,
                    onClick = { clickedNote ->
                        onNoteClick(clickedNote.id)
                    }
                )
            }
        }
    }

}
package ua.knu.maksym_pashchenko.notesapp.presentation.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NotesScreen(
    uiState: NotesListUiState,
    onSearchQueryChanged: (String) -> Unit,
    onSortTypeChanged: (NotesSortType) -> Unit,
    onNoteClick: (Long) -> Unit,
    onAddNoteClick: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNoteClick
            ) {
                Text(
                    text = "+"
                )
            }
        },
        modifier = Modifier.padding(16.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            OutlinedTextField(
                value = uiState.searchQuery,
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

            SortMenu(
                selectedSortType = uiState.sortType,
                onSortTypeChanged = onSortTypeChanged
            )

            when {
                uiState.allNotesCount == 0 -> {
                    EmptyNotesState(
                        modifier = Modifier.weight(1f)
                    )
                }

                uiState.notes.isEmpty() -> {
                    EmptySearchState(
                        query = uiState.searchQuery,
                        modifier = Modifier.weight(1f)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = uiState.notes,
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
        }
    }
}
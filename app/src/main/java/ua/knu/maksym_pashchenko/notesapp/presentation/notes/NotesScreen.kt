package ua.knu.maksym_pashchenko.notesapp.presentation.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.knu.maksym_pashchenko.notesapp.presentation.notes.empty_states.EmptyNotesState
import ua.knu.maksym_pashchenko.notesapp.presentation.notes.empty_states.EmptySearchState
import ua.knu.maksym_pashchenko.notesapp.presentation.notes.elements.NoteCard
import ua.knu.maksym_pashchenko.notesapp.presentation.notes.elements.SearchField
import ua.knu.maksym_pashchenko.notesapp.presentation.notes.elements.SortMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    uiState: NotesListUiState,
    onSearchQueryChanged: (String) -> Unit,
    onSortTypeChanged: (NotesSortType) -> Unit,
    onNoteClick: (Long) -> Unit,
    onAddNoteClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Notes"
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNoteClick
            ) {
                Text(
                    text = "+",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(
                top = 8.dp,
                bottom = 88.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                SearchField(
                    query = uiState.searchQuery,
                    onSearchQueryChanged = onSearchQueryChanged,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }


            item {
                SortMenu(
                    selectedSortType = uiState.sortType,
                    onSortTypeChanged = onSortTypeChanged
                )
            }

            when {
                uiState.allNotesCount == 0 -> {
                    item {
                        EmptyNotesState(
                            modifier = Modifier.fillParentMaxHeight(0.7f)
                        )
                    }
                }

                uiState.notes.isEmpty() -> {
                    item {
                        EmptySearchState(
                            query = uiState.searchQuery,
                            modifier = Modifier.fillParentMaxHeight(0.7f)
                        )
                    }

                }

                else -> {
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
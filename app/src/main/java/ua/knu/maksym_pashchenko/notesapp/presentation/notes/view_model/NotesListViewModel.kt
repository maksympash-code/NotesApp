package ua.knu.maksym_pashchenko.notesapp.presentation.notes.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import ua.knu.maksym_pashchenko.notesapp.domain.repository.NotesRepository
import ua.knu.maksym_pashchenko.notesapp.presentation.notes.NotesListUiState
import ua.knu.maksym_pashchenko.notesapp.presentation.notes.NotesSortType

class NotesListViewModel (
    private val repository: NotesRepository
): ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _sortType = MutableStateFlow(NotesSortType.DATE_DESC)

    val uiState: StateFlow<NotesListUiState> = combine(
        repository.getAllNotes(),
        _searchQuery,
        _sortType
    ) { notes, query, sortType ->

        val filteredNotes = notes
            .filter { note ->
                query.isBlank() ||
                        note.title.contains(query, ignoreCase = true) ||
                        note.content.contains(query, ignoreCase = true)
            }.let { filtered ->
                when (sortType) {
                    NotesSortType.DATE_DESC -> {
                        filtered.sortedByDescending { note ->
                            note.updatedAt
                        }
                    }

                    NotesSortType.DATE_ASC -> {
                        filtered.sortedBy { note ->
                            note.updatedAt
                        }
                    }

                    NotesSortType.TITLE_ASC -> {
                        filtered.sortedBy { note ->
                            note.title.lowercase()
                        }
                    }

                    NotesSortType.TITLE_DESC -> {
                        filtered.sortedByDescending { note ->
                            note.title.lowercase()
                        }
                    }
                }
            }

        NotesListUiState(
            notes = filteredNotes,
            searchQuery = query,
            sortType = sortType,
            allNotesCount = notes.size
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = NotesListUiState()
    )

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onSortTypeChanged(sortType: NotesSortType) {
        _sortType.value = sortType
    }
}
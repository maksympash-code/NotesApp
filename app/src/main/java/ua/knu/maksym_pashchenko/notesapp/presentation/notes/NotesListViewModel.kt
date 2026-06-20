package ua.knu.maksym_pashchenko.notesapp.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import ua.knu.maksym_pashchenko.notesapp.domain.model.Note
import ua.knu.maksym_pashchenko.notesapp.domain.repository.NotesRepository

class NotesListViewModel (
    private val repository: NotesRepository
): ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _sortType = MutableStateFlow(NotesSortType.DATE_DESC)
    val sortType: StateFlow<NotesSortType> = _sortType.asStateFlow()

    val notes: StateFlow<List<Note>> = combine(
        repository.getAllNotes(),
        _searchQuery,
        _sortType
    ) { notes, query, sortType ->
        val trimmedQuery = query.trim()

        val filteredNotes = if (trimmedQuery.isBlank()) {
            notes
        } else {
            notes.filter { note ->
                note.title.contains(trimmedQuery, ignoreCase = true) ||
                        note.content.contains(trimmedQuery, ignoreCase = true)
            }
        }

        when (sortType) {
            NotesSortType.DATE_DESC -> {
                filteredNotes.sortedByDescending { note ->
                    note.updatedAt
                }
            }

            NotesSortType.DATE_ASC -> {
                filteredNotes.sortedBy { note ->
                    note.updatedAt
                }
            }

            NotesSortType.TITLE_ASC -> {
                filteredNotes.sortedBy { note ->
                    note.title.lowercase()
                }
            }

            NotesSortType.TITLE_DESC -> {
                filteredNotes.sortedByDescending { note ->
                    note.title.lowercase()
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onSortTypeChanged(sortType: NotesSortType) {
        _sortType.value = sortType
    }
}
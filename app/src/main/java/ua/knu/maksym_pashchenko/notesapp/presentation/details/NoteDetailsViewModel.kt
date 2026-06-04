package ua.knu.maksym_pashchenko.notesapp.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.knu.maksym_pashchenko.notesapp.domain.model.Note
import ua.knu.maksym_pashchenko.notesapp.domain.repository.NotesRepository

class NoteDetailsViewModel(
    private val repository: NotesRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(NoteDetailsUiState())
    val uiState: StateFlow<NoteDetailsUiState> = _uiState.asStateFlow()

    private var loadedNoteId: Long? = null

    fun loadNote(noteId: Long) {
        if (loadedNoteId == noteId && _uiState.value.note != null) {
            return
        }

        loadedNoteId = noteId

        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(isLoading = true)
            }

            val note = repository.getNoteById(noteId)

            _uiState.update { state ->
                state.copy(
                    note = note,
                    isLoading = false,
                )
            }
        }
    }

    fun deleteNote(
        onDeleted: (Note) -> Unit
    ) {
        val note = _uiState.value.note ?: return

        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(isDeleting = true)
            }

            repository.deleteNoteById(note.id)

            _uiState.update { state ->
                state.copy(
                    note = null,
                    isDeleting = false
                )
            }

            onDeleted(note)
        }
    }
}
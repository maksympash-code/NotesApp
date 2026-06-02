package ua.knu.maksym_pashchenko.notesapp.presentation.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.knu.maksym_pashchenko.notesapp.domain.model.Note
import ua.knu.maksym_pashchenko.notesapp.domain.repository.NotesRepository

class NoteEditViewModel(
    private val repository: NotesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteEditUiState())
    val uiState: StateFlow<NoteEditUiState> = _uiState.asStateFlow()

    private var currentNoteId: Long? = null
    private var currentCreatedAt: Long? = null

    private var initialized = false
    private var initializedNoteId: Long? = null

    fun loadNote(noteId: Long?) {
        if (initialized && initializedNoteId == noteId) {
            return
        }

        initialized = true
        initializedNoteId = noteId

        if (noteId == null) {
            currentNoteId = null
            currentCreatedAt = null
            _uiState.update {
                NoteEditUiState()
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(isLoading = true)
            }

            val note = repository.getNoteById(noteId)

            if (note == null) {
                currentNoteId = null
                currentCreatedAt = null

                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        titleTouched = true
                    )
                }
                return@launch
            }

            currentNoteId = note.id
            currentCreatedAt = note.createdAt

            _uiState.update {
                NoteEditUiState(
                    title = note.title,
                    content = note.content,
                    isSaving = false,
                    isLoading = false,
                    titleTouched = false,
                    contentTouched = false
                )
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        _uiState.update { currentState ->
            currentState.copy(
                title = newTitle,
                titleTouched = true
            )
        }
    }

    fun onContentChange(newContent: String) {
        _uiState.update { currentState ->
            currentState.copy(
                content = newContent,
                contentTouched = true
            )
        }
    }

    fun saveNote(
        onSaved: () -> Unit,
    ) {
        val currentState = _uiState.value

        val title = currentState.title.trim()
        val content = currentState.content.trim()

        if (title.isBlank()) {
            _uiState.update { state ->
                state.copy(titleTouched = true)
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(isSaving = true)
            }

            val currentTime = System.currentTimeMillis()

            val noteId = currentNoteId

            if (noteId == null) {
                repository.insertNote(
                    Note(
                        id = 0L,
                        title = title,
                        content = content,
                        createdAt = currentTime,
                        updatedAt = currentTime
                    )
                )
            } else {
                repository.updateNote(
                    Note(
                        id = noteId,
                        title = title,
                        content = content,
                        createdAt = currentCreatedAt ?: currentTime,
                        updatedAt = currentTime
                    )
                )
            }

            _uiState.update { state ->
                state.copy(isSaving = false)
            }

            onSaved()
        }
    }
}
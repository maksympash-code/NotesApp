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
    private val repository: NotesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteEditUiState())
    val uiState: StateFlow<NoteEditUiState> = _uiState.asStateFlow()

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
        onSaved: () -> Unit
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

            repository.insertNote(
                Note(
                    id = 0L,
                    title = title,
                    content = content,
                    createdAt = currentTime,
                    updatedAt = currentTime
                )
            )

            _uiState.update { state ->
                state.copy(isSaving = false)
            }

            onSaved()
        }
    }
}
package ua.knu.maksym_pashchenko.notesapp.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.knu.maksym_pashchenko.notesapp.domain.model.Note
import ua.knu.maksym_pashchenko.notesapp.domain.repository.NotesRepository

class NotesListViewModel (
    private val repository: NotesRepository
): ViewModel() {

    val notes: StateFlow<List<Note>> = repository.getAllNotes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun addTestNote() {
        viewModelScope.launch {
            val currentTime = System.currentTimeMillis()

            repository.insertNote(
                Note(
                    id = 0L,
                    title = "Test note",
                    content = "This note was inserted into Room database.",
                    createdAt = currentTime,
                    updatedAt = currentTime
                )
            )
        }
    }
}
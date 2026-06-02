package ua.knu.maksym_pashchenko.notesapp.presentation.details

import ua.knu.maksym_pashchenko.notesapp.domain.model.Note

data class NoteDetailsUiState(
    val note: Note? = null,
    val isLoading: Boolean = false,
    val isDeleting: Boolean = false
)

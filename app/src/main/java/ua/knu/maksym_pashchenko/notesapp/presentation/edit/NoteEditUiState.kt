package ua.knu.maksym_pashchenko.notesapp.presentation.edit

data class NoteEditUiState(
    val title: String = "",
    val content: String = "",
    val isSaving: Boolean = false,
    val titleError: Boolean = false
)
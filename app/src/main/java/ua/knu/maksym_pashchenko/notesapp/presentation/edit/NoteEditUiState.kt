package ua.knu.maksym_pashchenko.notesapp.presentation.edit

data class NoteEditUiState(
    val title: String = "",
    val content: String = "",
    val isSaving: Boolean = false,
    val titleTouched: Boolean = false,
    val contentTouched: Boolean = false
) {
    val isTitleValid: Boolean
        get() = title.isNotBlank()

    val showTitleError: Boolean
        get() = titleTouched && title.isBlank()

    val showContextWarning: Boolean
        get() = contentTouched && content.isBlank()

    val canSave: Boolean
        get() = isTitleValid && !isSaving
}
package ua.knu.maksym_pashchenko.notesapp.presentation.notes

import ua.knu.maksym_pashchenko.notesapp.domain.model.Note

data class NotesListUiState(
    val notes: List<Note> = emptyList(),
    val searchQuery: String = "",
    val sortType: NotesSortType = NotesSortType.DATE_DESC,
    val allNotesCount: Int = 0,
)

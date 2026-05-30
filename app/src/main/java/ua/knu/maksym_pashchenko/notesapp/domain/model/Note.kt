package ua.knu.maksym_pashchenko.notesapp.domain.model

data class Note(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long,
)

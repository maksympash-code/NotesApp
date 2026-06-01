package ua.knu.maksym_pashchenko.notesapp.data.local.mappers

import ua.knu.maksym_pashchenko.notesapp.data.local.entity.NoteEntity
import ua.knu.maksym_pashchenko.notesapp.domain.model.Note

fun NoteEntity.toDomain(): Note = Note(
    id = id,
    title = title,
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Note.toEntity(): NoteEntity = NoteEntity(
    id = id,
    title = title,
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt
)
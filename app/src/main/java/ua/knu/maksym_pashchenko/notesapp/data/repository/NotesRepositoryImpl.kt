package ua.knu.maksym_pashchenko.notesapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.knu.maksym_pashchenko.notesapp.data.local.dao.NoteDao
import ua.knu.maksym_pashchenko.notesapp.data.local.mappers.toDomain
import ua.knu.maksym_pashchenko.notesapp.data.local.mappers.toEntity
import ua.knu.maksym_pashchenko.notesapp.domain.model.Note
import ua.knu.maksym_pashchenko.notesapp.domain.repository.NotesRepository

class NotesRepositoryImpl (
    private val noteDao: NoteDao
): NotesRepository {
    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { entities ->
            entities.map { entity ->
                entity.toDomain()
            }
        }
    }

    override suspend fun getNoteById(noteId: Long): Note? {
        return noteDao.getNoteById(noteId)?.toDomain()
    }

    override suspend fun insertNote(note: Note): Long {
        return noteDao.insertNote(note.toEntity())
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note.toEntity())
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note.toEntity())
    }

    override suspend fun deleteNoteById(noteId: Long) {
        noteDao.deleteNoteById(noteId)
    }

}
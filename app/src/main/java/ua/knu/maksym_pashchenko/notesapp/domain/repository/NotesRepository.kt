package ua.knu.maksym_pashchenko.notesapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ua.knu.maksym_pashchenko.notesapp.domain.model.Note

interface NotesRepository {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun getNoteById(noteId: Long): Note?
    suspend fun insertNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun deleteNoteById(noteId: Long)
}
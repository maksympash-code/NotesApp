package ua.knu.maksym_pashchenko.notesapp.presentation.edit

import ua.knu.maksym_pashchenko.notesapp.presentation.notes.NotesListViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ua.knu.maksym_pashchenko.notesapp.domain.repository.NotesRepository

class NoteEditViewModelFactory(
    private val repository: NotesRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteEditViewModel::class.java)) {
            return NoteEditViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
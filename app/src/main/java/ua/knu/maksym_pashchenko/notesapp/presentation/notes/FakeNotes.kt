package ua.knu.maksym_pashchenko.notesapp.presentation.notes

import ua.knu.maksym_pashchenko.notesapp.domain.model.Note

val fakeNotes = listOf<Note>(
    Note(
        id = 1L,
        title = "Перша нотатка",
        content = "Це тестовий текст першої нотатки.",
        createdAt = 1710000000000L,
        updatedAt = 1710000000000L
    ),
    Note(
        id = 2L,
        title = "План на день",
        content = "Створити модель Note, fake-дані та підготувати UI для списку.",
        createdAt = 1710003600000L,
        updatedAt = 1710003600000L
    ),
    Note(
        id = 3L,
        title = "Ідеї для NotesApp",
        content = "Пошук, сортування, редагування, видалення, Room, Navigation Compose.",
        createdAt = 1710007200000L,
        updatedAt = 1710007200000L
    ),
)
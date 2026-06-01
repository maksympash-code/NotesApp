package ua.knu.maksym_pashchenko.notesapp.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.knu.maksym_pashchenko.notesapp.domain.model.Note
import ua.knu.maksym_pashchenko.notesapp.presentation.util.formatTimestamp


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

@Composable
fun NoteDetailsScreen(
    noteId: Long,
    onBack: () -> Unit,
    onEditClick: (Long) -> Unit,
    onDeleteClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val note = fakeNotes.firstOrNull { it.id == noteId }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextButton(onClick = onBack) {
            Text(text = "Back")
        }

        if (note == null) {
            Text(
                text = "Note not found",
                style = MaterialTheme.typography.titleLarge
            )
            return@Column
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = {
                    onEditClick(note.id)
                }
            ) {
                Text(text = "Edit")
            }

            TextButton(
                onClick = {
                    onDeleteClick(note.id)
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(text = "Delete")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = note.title,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Created: ${formatTimestamp(note.createdAt)}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = "Updated: ${formatTimestamp(note.updatedAt)}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = note.content,
            style = MaterialTheme.typography.bodyLarge,
        )
    }

}
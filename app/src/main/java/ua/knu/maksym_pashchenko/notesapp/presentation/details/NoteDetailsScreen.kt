package ua.knu.maksym_pashchenko.notesapp.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.knu.maksym_pashchenko.notesapp.presentation.notes.fakeNotes

@Composable
fun NoteDetailsScreen(
    noteId: Long,
    onBack: () -> Unit,
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

        Text(
            text = note.title,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Updated: ${note.updatedAt}",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = note.content,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
    }

}
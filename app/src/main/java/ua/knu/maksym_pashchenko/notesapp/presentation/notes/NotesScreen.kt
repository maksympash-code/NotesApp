package ua.knu.maksym_pashchenko.notesapp.presentation.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.knu.maksym_pashchenko.notesapp.domain.model.Note

@Composable
fun NotesScreen(
    notes: List<Note>,
    onNoteClick: (Long) -> Unit,
    onTestNoteClick: () -> Unit,
    onCreateNoteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Notes",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = onTestNoteClick,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Text("Add test note")
        }

        Button(
            onClick = onCreateNoteClick,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Create note")
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = notes,
                key = { note -> note.id }
            ) { note ->
                NoteCard(
                    note = note,
                    onClick = { clickedNote ->
                        onNoteClick(clickedNote.id)
                    }
                )
            }
        }
    }

}
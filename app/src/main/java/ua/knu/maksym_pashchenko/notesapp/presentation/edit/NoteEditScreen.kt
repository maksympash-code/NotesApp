package ua.knu.maksym_pashchenko.notesapp.presentation.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NoteEditScreen(
    noteId: Long?,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        var title by rememberSaveable {
            mutableStateOf("")
        }

        var content by rememberSaveable {
            mutableStateOf("")
        }

        val screenTitle = if (noteId == null) {
            "Create note"
        } else {
            "Edit note"
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextButton(
                onClick = onBack
            ) {
                Text("Back")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = screenTitle,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { newTittle ->
                    title = newTittle
                },
                label = {
                    Text(text = "Title")
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = content,
                onValueChange = { newContent ->
                    content = newContent
                },
                label = {
                    Text(text = "Content")
                },
                maxLines = 8,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        // Save logic will be added later
                    }
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}
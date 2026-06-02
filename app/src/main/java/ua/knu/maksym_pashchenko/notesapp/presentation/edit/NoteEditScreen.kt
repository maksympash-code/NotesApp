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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NoteEditScreen(
    noteId: Long?,
    uiState: NoteEditUiState,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isCreatedMode = noteId == null

    val screenTitle = if (isCreatedMode) {
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
            value = uiState.title,
            onValueChange = onTitleChange,
            label = {
                Text(text = "Title")
                },
            singleLine = true,
            isError = uiState.titleError,
            supportingText = {
                if (uiState.titleError){
                    Text(text = "Title cannot be empty")
                }
            },
            modifier = Modifier.fillMaxWidth()
            )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.content,
            onValueChange = onContentChange,
            label = {
                Text(text = "Content")
                },
            minLines = 8,
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
                onClick = onSaveClick,
                enabled = isCreatedMode &&
                uiState.title.isNotBlank() &&
                !uiState.isSaving
                ) {
                    Text(text = if (uiState.isSaving) "Saving ..."
                        else "Save"
                    )
                }
            }
        }
    }

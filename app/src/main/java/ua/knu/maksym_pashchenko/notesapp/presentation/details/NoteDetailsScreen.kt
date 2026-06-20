package ua.knu.maksym_pashchenko.notesapp.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.knu.maksym_pashchenko.notesapp.presentation.util.formatTimestamp

@Composable
fun NoteDetailsScreen(
    uiState: NoteDetailsUiState,
    onBack: () -> Unit,
    onEditClick: (Long) -> Unit,
    onDeleteConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDeleteDialog by rememberSaveable {
        mutableStateOf(false)
    }

    val note = uiState.note

    if (showDeleteDialog && note != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
            },
            title = {
                Text(text = "Delete note?")
            },
            text = {
                Text(text = "Are you sure you want to delete \"${note.title}\"? This action cannot be undone.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDeleteConfirm()
                    }
                ) {
                    Text(text = "Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text(text = "Cancel")
                }
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TextButton(
            onClick = onBack,
            enabled = !uiState.isDeleting
        ) {
            Text(text = "Back")
        }

        if (uiState.isLoading) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Loading note ...",
                style = MaterialTheme.typography.bodyMedium
            )

            return@Column
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
                },
                enabled = !uiState.isDeleting
            ) {
                Text(text = "Edit")
            }

            TextButton(
                onClick = {
                    showDeleteDialog = true
                },
                enabled = !uiState.isDeleting,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(text = if (uiState.isDeleting){
                    "Deleting ..."
                } else {
                    "Delete"
                })
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
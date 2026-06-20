package ua.knu.maksym_pashchenko.notesapp.presentation.notes.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchField(
    query: String,
    onSearchQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onSearchQueryChanged,
        modifier = modifier
            .fillMaxWidth(),
        singleLine = true,
        label = {
            Text("Search")
        },
        placeholder = {
            Text("Search by title or content")
        }
    )
}
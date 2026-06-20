package ua.knu.maksym_pashchenko.notesapp.presentation.notes.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
            .fillMaxWidth()
            .padding(top = 8.dp),
        singleLine = true,
        label = {
            Text("Search")
        },
        placeholder = {
            Text("Search by title or content")
        }
    )
}
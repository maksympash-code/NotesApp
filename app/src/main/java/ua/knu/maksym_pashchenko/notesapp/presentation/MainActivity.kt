package ua.knu.maksym_pashchenko.notesapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ua.knu.maksym_pashchenko.notesapp.navigation.NotesNavGraph
import ua.knu.maksym_pashchenko.notesapp.presentation.ui.theme.NotesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesAppTheme {
                NotesNavGraph()
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun NotesNavGraphPreview() {
    NotesAppTheme {
        NotesNavGraph()
    }
}
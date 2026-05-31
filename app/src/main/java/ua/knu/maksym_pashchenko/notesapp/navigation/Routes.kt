package ua.knu.maksym_pashchenko.notesapp.navigation

object Routes {
    const val NOTES_LIST = "notes_list"
    const val NOTES_DETAILS = "notes_details"
    const val NOTE_EDIT = "note_edit"

    const val NOTE_ID_ARGUMENT = "noteId"

    const val NOTE_DETAILS_ROUTE = "$NOTES_DETAILS/{$NOTE_ID_ARGUMENT}"
    const val NOTE_EDIT_ROUTE = "$NOTE_EDIT?$NOTE_ID_ARGUMENT={$NOTE_ID_ARGUMENT}"

    fun noteDetails(noteId: Long? = null): String {
        return "$NOTES_DETAILS/${noteId}"
    }

    fun noteEdit(noteId: Long? = null): String {
        return if (noteId == null) {
            "$NOTE_EDIT?$NOTE_ID_ARGUMENT=${noteId}"
        } else {
            NOTE_EDIT
        }
    }
}
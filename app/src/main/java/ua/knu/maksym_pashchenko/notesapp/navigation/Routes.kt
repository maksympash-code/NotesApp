package ua.knu.maksym_pashchenko.notesapp.navigation

object Routes {
    const val NOTES_LIST = "notes_list"
    const val NOTE_DETAILS = "note_details"
    const val NOTE_EDIT = "note_edit"

    const val NOTE_ID_ARGUMENT = "noteId"

    const val NOTE_DETAILS_ROUTE = "$NOTE_DETAILS/{$NOTE_ID_ARGUMENT}"
    const val NOTE_EDIT_ROUTE = "$NOTE_EDIT?$NOTE_ID_ARGUMENT={$NOTE_ID_ARGUMENT}"

    fun noteDetails(noteId: Long): String {
        return "$NOTE_DETAILS/$noteId"
    }

    fun noteEdit(noteId: Long? = null): String {
        return if (noteId == null) {
            NOTE_EDIT
        } else {
            "$NOTE_EDIT?$NOTE_ID_ARGUMENT=$noteId"
        }
    }
}
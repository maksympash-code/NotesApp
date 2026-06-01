# NotesApp

Local notes application built with **Kotlin**, **Jetpack Compose**, **Navigation Compose**, and **Room**.

This project is created as a training Android application for practicing:

- Jetpack Compose UI
- navigation between screens
- clean package structure
- Room database
- repository pattern
- local CRUD operations

---

## Current Status

The project currently has a working UI skeleton and prepared local database layer.

The UI still uses fake notes for displaying data, but the Room layer, mappers, and repository are already created.

---

## Implemented Features

- Basic Android project setup
- Jetpack Compose setup
- Material 3 setup
- Navigation Compose setup
- Basic package structure:
    - `data`
    - `domain`
    - `presentation`
    - `navigation`
- Domain model `Note`
- Fake notes for temporary UI testing
- Notes list screen
- Note card UI
- Details screen
- Edit screen placeholder
- Navigation flow:
    - Notes list
    - Note details
    - Note edit route
- Room dependencies
- Room `NoteEntity`
- Room `NoteDao`
- Room `NotesDatabase`
- Mapper functions:
    - `NoteEntity.toDomain()`
    - `Note.toEntity()`
- Repository abstraction:
    - `NotesRepository`
    - `NotesRepositoryImpl`

---

## Current App Flow

```text
Notes list → Note details → Edit route
```

At the moment, notes on the UI are loaded from `fakeNotes`.

Room database is prepared, but it is not connected to the UI yet.

---

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- Room
- Kotlin Coroutines
- Flow
- KSP

---

## Project Structure

```text
app/src/main/java/ua/knu/maksym_pashchenko/notesapp

├── data
│   ├── local
│   │   ├── dao
│   │   │   └── NoteDao.kt
│   │   ├── database
│   │   │   └── NotesDatabase.kt
│   │   ├── entity
│   │   │   └── NoteEntity.kt
│   │   └── mappers
│   │       └── Mappers.kt
│   │
│   └── repository
│       └── NotesRepositoryImpl.kt
│
├── domain
│   ├── model
│   │   └── Note.kt
│   └── repository
│       └── NotesRepository.kt
│
├── navigation
│   ├── NotesNavGraph.kt
│   └── Routes.kt
│
└── presentation
    ├── MainActivity.kt
    ├── details
    │   └── NoteDetailsScreen.kt
    ├── edit
    │   └── NoteEditScreen.kt
    ├── notes
    │   ├── FakeNotes.kt
    │   ├── NoteCard.kt
    │   └── NotesScreen.kt
    ├── ui
    │   └── theme
    └── util
```

---

## Domain Model

```kotlin
data class Note(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long
)
```

The domain model is separated from the Room entity.  
This helps keep the database layer independent from the UI layer.

---

## Room Entity

```kotlin
@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long
)
```

---

## DAO

The project contains `NoteDao` with basic CRUD operations:

```kotlin
@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY updatedAt DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteById(noteId: Long): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Long)
}
```

---

## Database

The project contains `NotesDatabase`.

It connects Room with `NoteEntity` and provides access to `NoteDao`.

```kotlin
@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
```

---

## Mappers

The project uses mapper functions to convert Room entities to domain models and back.

```kotlin
fun NoteEntity.toDomain(): Note

fun Note.toEntity(): NoteEntity
```

This keeps Room models separated from the domain layer.

---

## Repository

The project uses a repository interface in the domain layer:

```kotlin
interface NotesRepository {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun getNoteById(noteId: Long): Note?
    suspend fun insertNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun deleteNoteById(noteId: Long)
}
```

The implementation is located in the data layer:

```text
data/repository/NotesRepositoryImpl.kt
```

`NotesRepositoryImpl` works with `NoteDao` and uses mappers to convert data between `NoteEntity` and `Note`.

---

## Screens

### NotesScreen

Displays the list of notes using `LazyColumn`.

Current data source:

```kotlin
fakeNotes
```

Future data source:

```kotlin
Room database → Repository → ViewModel → UI
```

---

### NoteCard

Displays one note item.

Shows:

- note title
- short content preview
- updated date
- clickable card behavior

---

### NoteDetailsScreen

Displays selected note details.

Shows:

- title
- content
- created date
- updated date
- Back button
- Edit button
- Delete button

Currently, the selected note is loaded from `fakeNotes`.

---

### NoteEditScreen

Currently implemented as a placeholder screen.

It shows:

- `"Create note screen"` if `noteId == null`
- `"Edit note screen, noteId = ..."` if `noteId` exists

Full create/edit logic is not implemented yet.

---

## Navigation

Navigation is handled by `NotesNavGraph`.

Routes are stored in `Routes.kt`.

Current routes:

```kotlin
notes_list
note_details/{noteId}
note_edit?noteId={noteId}
```

Current navigation flow:

```text
NotesScreen → NoteDetailsScreen → NoteEditScreen
```

---

## Not Implemented Yet

- Connecting Room data to UI
- ViewModel layer
- Creating notes from UI
- Editing notes from UI
- Deleting notes from UI
- Search notes
- Sorting notes
- Empty state screen
- Loading state
- Error handling
- Better UI for create/edit screen

---

## Next Steps

### 1. Add ViewModel for NotesScreen

Create `NotesViewModel`.

It should receive `NotesRepository` and expose notes as state.

Example:

```kotlin
val notes: StateFlow<List<Note>>
```

---

### 2. Replace fake notes with Room data

Current version:

```kotlin
items = fakeNotes
```

Future version:

```kotlin
items = notes
```

---

### 3. Add create note logic

Implement note creation in `NoteEditScreen`.

When `noteId == null`, the screen should create a new note.

---

### 4. Add edit note logic

When `noteId != null`, the screen should load an existing note and update it after saving.

---

### 5. Add delete note logic

Connect the Delete button on `NoteDetailsScreen` to repository delete logic.

---

### 6. Add search

Add search by:

- title
- content

---

### 7. Add sorting

Add sorting by:

- updated date
- created date
- title

---

## Development Progress

### Week 1 — UI Skeleton

| Day | Task | Status |
|---|---|---|
| Day 1 | Basic project setup | Done |
| Day 2 | Domain model and fake notes | Done |
| Day 3 | Notes list screen | Done |
| Day 4 | Note card UI | Done |
| Day 5 | Navigation Compose | Done |
| Day 6 | Details screen | Done |
| Day 7 | UI flow cleanup | Mostly done |

---

### Week 2 — Room + CRUD

| Day | Task | Status |
|---|---|---|
| Day 8 | Room Entity | Done |
| Day 9 | DAO | Done |
| Day 10 | Room Database | Done |
| Day 11 | Entity ↔ Domain mappers | Done |
| Day 12 | Repository | Done |
| Day 13 | Connect Room to UI | Not done yet |
| Day 14 | First real CRUD flow | Not done yet |

---

## How to Run

1. Clone the repository:

```bash
git clone https://github.com/maksympash-code/NotesApp.git
```

2. Open the project in Android Studio.

3. Sync Gradle.

4. Run the app on an emulator or Android device.

---

## Project Goal

The goal of this project is to build a local notes app with:

- notes list
- note details screen
- create note
- edit note
- delete note
- search
- sorting
- local persistence with Room
- clean architecture-style package separation

---

## Author

Created by [maksympash-code](https://github.com/maksympash-code)
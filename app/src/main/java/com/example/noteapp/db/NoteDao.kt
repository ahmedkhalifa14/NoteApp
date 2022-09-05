package com.example.noteapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.noteapp.model.Note
import com.example.noteapp.util.Resource

@Dao
interface NoteDao {
//    suspend fun addLocalNote(note: Note,result: (Resource<Pair<Note, String>>) -> Unit)
//    suspend fun updateLocalNote(note: Note,result: (Resource<String>) -> Unit)
//    suspend fun deleteLocalNote(note: Note, result: (Resource<String>) -> Unit)
//    fun getAllLocalNotes( result: (Resource<List<Note>>) -> Unit)
//    fun searchForLocalNote(query: String, result: (Resource<String>) -> Unit)
//


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM NOTE_TABLE ORDER BY id DESC")
     fun getAllNotes():List<Note>

    @Query("SELECT * FROM NOTE_TABLE WHERE noteTitle LIKE:query OR noteBody LIKE:query")
     fun searchForNote(query: String): List<Note>


}
package com.example.noteapp.repo

import androidx.lifecycle.LiveData
import com.example.noteapp.db.NoteDatabase
import com.example.noteapp.model.Note
import com.example.noteapp.util.Resource

interface LocalRepo {

    suspend fun addLocalNote(note: Note)
    suspend fun updateLocalNote(note: Note)
    suspend fun deleteLocalNote(note: Note)
    fun getAllLocalNotes()
    fun searchForLocalNote(query: String)


//       suspend fun addNote(note:Note)=db.noteDao().addNote(note)
//    suspend fun updateNote(note: Note)=db.noteDao().updateNote(note)
//    suspend fun deleteNote(note: Note)=db.noteDao().deleteNote(note)
//    fun getAllNotes()=db.noteDao().getAllNotes()
//    fun searchForNote(query:String)=db.noteDao().searchForNote(query)*/
//
//    /* suspend fun insertOrUpdateUser(user: User)
//    suspend fun deleteUser(user: User)
//    suspend fun getUser(): List<User>
}
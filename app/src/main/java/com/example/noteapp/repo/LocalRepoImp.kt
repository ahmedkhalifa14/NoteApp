package com.example.noteapp.repo

import com.example.noteapp.db.NoteDao
import com.example.noteapp.db.NoteDatabase
import com.example.noteapp.model.Note
import com.example.noteapp.util.Resource
import javax.inject.Inject

class LocalRepoImp @Inject constructor(private val noteDatabase: NoteDatabase) : LocalRepo {
    override suspend fun addLocalNote(note: Note) =
        noteDatabase.noteDao().addNote(note)
    override suspend fun updateLocalNote(note: Note) =
        noteDatabase.noteDao().updateNote(note)
    override suspend fun deleteLocalNote(note: Note) =
        noteDatabase.noteDao().deleteNote(note)
    override fun getAllLocalNotes() {
        noteDatabase.noteDao().getAllNotes()
    }
    override fun searchForLocalNote(query: String) {}

}
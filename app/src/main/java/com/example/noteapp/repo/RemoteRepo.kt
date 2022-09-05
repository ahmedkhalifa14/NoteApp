package com.example.noteapp.repo

import android.net.Uri
import com.example.noteapp.db.NoteDatabase
import com.example.noteapp.model.Note
import com.example.noteapp.model.User
import com.example.noteapp.util.Resource
import com.google.firebase.firestore.FirebaseFirestore

interface RemoteRepo {

    fun getFirebaseNotes(user: User?, result: (Resource<List<Note>>) -> Unit)
    fun addFirebaseNote(note: Note, result: (Resource<Pair<Note,String>>) -> Unit)
    fun updateFirebaseNote(note: Note, result: (Resource<String>) -> Unit)
    fun deleteFirebaseNote(note: Note, result: (Resource<String>) -> Unit)
    suspend fun uploadFirebaseSingleFile(fileUri: Uri, onResult: (Resource<Uri>) -> Unit)
    suspend fun uploadFirebaseMultipleFile(fileUri: List<Uri>, onResult: (Resource<List<Uri>>) -> Unit)

}
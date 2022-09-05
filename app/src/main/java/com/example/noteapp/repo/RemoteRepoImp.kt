package com.example.noteapp.repo

import android.net.Uri
import com.example.noteapp.model.Note
import com.example.noteapp.model.User
import com.example.noteapp.util.FireStoreCollection
import com.example.noteapp.util.FireStoreDocumentField
import com.example.noteapp.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject

class RemoteRepoImp @Inject constructor(private val database: FirebaseFirestore) : RemoteRepo {

    override fun getFirebaseNotes(user: User?, result: (Resource<List<Note>>) -> Unit) {
        database.collection(FireStoreCollection.NOTE)
            .whereEqualTo(FireStoreDocumentField.USER_ID, user?.id)
            .orderBy(FireStoreDocumentField.DATE, Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                val allNotes = arrayListOf<Note>()
                for (Notes in it) {
                    val note = Notes.toObject(Note::class.java)
                    allNotes.add(note)
                }
                result.invoke(Resource.Success(allNotes))
            }
            .addOnFailureListener {
                result.invoke(Resource.Error(it.localizedMessage as String))
            }
    }

    override fun addFirebaseNote(note: Note, result: (Resource<Pair<Note, String>>) -> Unit) {
        val document = database.collection(FireStoreCollection.NOTE).document()
        note.id = document.id
        document.set(note)
            .addOnSuccessListener {
                result.invoke(Resource.Success(Pair(note, "Note has been added successfully")))
            }
            .addOnFailureListener {
                result.invoke(Resource.Error(it.localizedMessage as String))
            }

    }

    override fun updateFirebaseNote(note: Note, result: (Resource<String>) -> Unit) {
        val document = database.collection(FireStoreCollection.NOTE).document(note.id)
        document.set(note, SetOptions.merge())
            .addOnSuccessListener {
                result.invoke(Resource.Success("Note has been updated successfully"))
            }
            .addOnFailureListener {
                result.invoke(
                    Resource.Error(it.localizedMessage as String)
                )
            }
    }

    override fun deleteFirebaseNote(note: Note, result: (Resource<String>) -> Unit) {
        database.collection(FireStoreCollection.NOTE).document(note.id)
            .delete()
            .addOnSuccessListener {
                result.invoke(Resource.Success("Note has been deleted successfully"))
            }
            .addOnFailureListener {
                result.invoke(
                    Resource.Error(it.message.toString())
                )
            }
    }

    override suspend fun uploadFirebaseSingleFile(fileUri: Uri, onResult: (Resource<Uri>) -> Unit) {
    }

    override suspend fun uploadFirebaseMultipleFile(
        fileUri: List<Uri>,
        onResult: (Resource<List<Uri>>) -> Unit
    ) {

    }
}
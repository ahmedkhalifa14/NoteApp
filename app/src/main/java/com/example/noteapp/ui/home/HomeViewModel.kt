package com.example.noteapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.model.Note
import com.example.noteapp.model.User
import com.example.noteapp.repo.LocalRepo
import com.example.noteapp.repo.RemoteRepo
import com.example.noteapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor
    ( val remoteRepo: RemoteRepo,val localRepo: LocalRepo) :
    ViewModel() {


    /*****************add note**************************/
    //local
//    private val _addLocalNote = MutableLiveData<Resource<Pair<Note, String>>>()
//    val addLocalNote: LiveData<Resource<Pair<Note, String>>>
//        get() = _addLocalNote
    fun addLocalNote(note: Note) = viewModelScope.launch {
        localRepo.addLocalNote(note)
//        _addLocalNoteNote.value = Resource.Loading()
//        localRepo.addLocalNote(note) {
//            _addLocalNote.value = it
    }

    //remote
    private val _addNote = MutableLiveData<Resource<Pair<Note, String>>>()
    val addNote: LiveData<Resource<Pair<Note, String>>>
        get() = _addNote

    fun addFirebaseNote(note: Note) = viewModelScope.launch {
        _addNote.value = Resource.Loading()
        remoteRepo.addFirebaseNote(note) {
            _addNote.value = it
        }
    }

    /****************** Update Note **************************/
    //local
    fun updateLocalNote(note: Note) = viewModelScope.launch {
        localRepo.updateLocalNote(note)
    }
    //remote
    private val _updateNote = MutableLiveData<Resource<String>>()
    val updateNote: LiveData<Resource<String>>
        get() = _updateNote

    fun updateFirebaseNote(note: Note) = viewModelScope.launch {
        _updateNote.value = Resource.Loading()
        remoteRepo.updateFirebaseNote(note) {
            _updateNote.value = it
        }

    }

    /*****************delete note************************/
   // local
    fun deleteLocalNote(note: Note) = viewModelScope.launch {
        localRepo.deleteLocalNote(note)
    }
    //remote
    private val _deleteNote = MutableLiveData<Resource<String>>()
    val deleteNote: LiveData<Resource<String>>
        get() = _deleteNote

    fun deleteFirebaseNote(note: Note) = viewModelScope.launch {
        _deleteNote.value = Resource.Loading()
        remoteRepo.deleteFirebaseNote(note) {
            _deleteNote.value = it
        }
    }
    /*****************Get All Notes***************************/
    //local
    fun getAllLocalNotes() = viewModelScope.launch {
        localRepo.getAllLocalNotes()
    }

    //remote
    private val _notes = MutableLiveData<Resource<List<Note>>>()
    val note: LiveData<Resource<List<Note>>>
        get() = _notes

    fun getAllFirebaseNotes(user: User) = viewModelScope.launch {
        _notes.value = Resource.Loading()
        remoteRepo.getFirebaseNotes(user) {
            _notes.value = it
        }
    }


}
package com.example.noteapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.noteapp.model.Note
import com.example.noteapp.model.User
import com.example.noteapp.repo.LocalRepo
import com.example.noteapp.repo.LocalRepoImp
import com.example.noteapp.repo.RemoteRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
//class NoteViewModel @Inject constructor(private val noteRepository: LocalRepo, private val remoteRepo: RemoteRepo) :
//    ViewModel() {
////local
//    fun addNote(note: Note) = viewModelScope.launch {
//        noteRepository.addNote(note)
//    }
//
//    fun deleteNote(note: Note) = viewModelScope.launch {
//        noteRepository.deleteNote(note)
//    }
//
//    fun updateNote(note: Note) = viewModelScope.launch {
//        noteRepository.updateNote(note)
//    };
//
//    fun getAllNotes() = noteRepository.getAllNotes()
//
//    fun searchForNote(query:String)=noteRepository.searchForNote(query)
////remote
//private val _notes =MutableLiveData<List<Note>>()
//    val note:LiveData<List<Note>>
//    get() = _notes
//
////    fun getRemoteNote(){
////        _notes.value = remoteRepo.getFirebaseNotes()
////    }
//
//
//
//
//}
package com.example.noteapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.repo.LocalRepo
import com.example.noteapp.repo.RemoteRepo

//class NoteViewModelFactory(private val noteRepository: LocalRepo, private val remoteRepo: RemoteRepo) :
//    ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return NoteViewModel(
//            noteRepository,remoteRepo) as T
//    }
//}
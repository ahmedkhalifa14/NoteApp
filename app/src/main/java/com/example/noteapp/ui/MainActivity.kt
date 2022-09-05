package com.example.noteapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.noteapp.R
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.db.NoteDatabase
import com.example.noteapp.repo.LocalRepo
import com.example.noteapp.repo.RemoteRepo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
//    private val noteRepository :LocalRepo by lazy {LocalRepo(
//        NoteDatabase(this)
//    )  }
//    private val remoteRepo:RemoteRepo by lazy{RemoteRepo()}
//    private val  viewModelFactory:NoteViewModelFactory by lazy{NoteViewModelFactory(
//        application,
//        noteRepository,
//        remoteRepo
//    )}
//     val noteViewModel: NoteViewModel by lazy { ViewModelProvider(
//        this,
//        viewModelFactory,
//        ).get(NoteViewModel::class.java)
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(findNavController(R.id.fragment))
        //setupNoteViewModel()
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController=findNavController(R.id.fragment)
        return navController.navigateUp()||super.onSupportNavigateUp()

    }

//    fun setupNoteViewModel() {
//        val noteRepository = NoteRepository(
//        NoteDatabase(this)
//        )
//        val viewModelFactory = NoteViewModelFactory(
//            application,
//            noteRepository
//        )
//        noteViewModel= ViewModelProvider(
//            this,
//            viewModelFactory,
//
//        ).get(NoteViewModel::class.java)
//
//    }
}


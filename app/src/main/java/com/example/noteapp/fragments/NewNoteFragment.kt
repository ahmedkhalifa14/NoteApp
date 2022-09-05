package com.example.noteapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.noteapp.ColorSpinnerAdapter
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentNewNoteBinding
import com.example.noteapp.model.AudioRecord
import com.example.noteapp.model.ColorObject
import com.example.noteapp.model.Note
import com.example.noteapp.ui.auth.AuthViewModel
import com.example.noteapp.ui.home.HomeViewModel
import com.example.noteapp.util.*
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NewNoteFragment : Fragment(R.layout.fragment_new_note) {
    private var _binding: FragmentNewNoteBinding? = null
    private val binding get() = _binding!!
    var objNote: Note? = null
    lateinit var selectedColor: ColorObject
    private val homeViewModel: HomeViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var mView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView = view
        loadColorSpinner()
        observer()
    }
    private fun getNote(): Note {
        return Note(
            id = objNote?.id ?: "",
            noteTitle = binding.noteTitleEt.text.toString(),
            noteBody = binding.noteBodyEt.text.toString(),
        //    images = getImageUrls(),
            date = Date(),
            colorObject = selectedColor

        ).apply { authViewModel.getSession { this.user_id = it?.id ?: "" } }
    }
    private fun observer() {
        homeViewModel.addNote.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.newNoteProgressBar.show()
                }
                is Resource.Error -> {
                    binding.newNoteProgressBar.hide()
                }
                is Resource.Success -> {
                    binding.newNoteProgressBar.hide()
                    activity?.showToast(it.data!!.second)
                    enableUI(false)
                }
            }
        }

    }
    private fun loadColorSpinner() {
        selectedColor = ColorList().defaultColor
        binding.colorSpinner.apply {
            adapter = ColorSpinnerAdapter(context, ColorList().basicColors())
            setSelection(ColorList().colorPosition(selectedColor), false)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener
            {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long)
                {
                    selectedColor = ColorList().basicColors()[position]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }
    private fun saveNote(view: View) {
   homeViewModel.addFirebaseNote(getNote())
        Snackbar.make(mView, "Your note saved successfully", Snackbar.LENGTH_SHORT).show()
        mView.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)
    }
    private fun enableUI(isDisable: Boolean = false) {
        binding.noteTitleEt.isEnabled = isDisable
        binding.noteBodyEt.isEnabled = isDisable
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.save_menu -> {
                saveNote(mView)
            }
        }
        return super.onOptionsItemSelected(item)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.new_note_menu, menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
package com.example.noteapp.fragments

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.noteapp.ColorSpinnerAdapter
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentUpdateNoteBinding
import com.example.noteapp.model.AudioRecord
import com.example.noteapp.model.ColorObject
import com.example.noteapp.model.Note
import com.example.noteapp.ui.auth.AuthViewModel
import com.example.noteapp.ui.home.HomeViewModel
import com.example.noteapp.util.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class UpdateNoteFragment : Fragment() {
    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding
    lateinit var selectedColor: ColorObject
    var objNote: Note? = null
    private lateinit var mView: View
    private val args: UpdateNoteFragmentArgs by navArgs()
    private lateinit var currentNote: Note
    private val homeViewModel: HomeViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView = view
        observer()
        loadColorSpinner()
         currentNote = args.note
        displayCurrentNote()
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.update_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }
    private fun displayCurrentNote(){
        binding!!.noteTitleEt.setText(currentNote.noteTitle)
        binding!!.noteBodyEt.setText(currentNote.noteBody)
        selectedColor=currentNote.colorObject!!
    }
    private fun observer(){
        homeViewModel.updateNote.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding!!.updateNoteProgressBar.show()
                }
                is Resource.Error -> {
                    binding!!.updateNoteProgressBar.hide()
                    activity?.showToast(it.message.toString())
                }
                is Resource.Success -> {
                    binding!!.updateNoteProgressBar.hide()
                    activity?.showToast(it.data.toString())
                    enableUI(false)
                }
            }
        }

        homeViewModel.deleteNote.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding!!.updateNoteProgressBar.show()
                }
                is Resource.Error -> {
                    binding!!.updateNoteProgressBar.hide()
                    activity?.showToast(it.message.toString())
                }
                is Resource.Success -> {
                    binding!!.updateNoteProgressBar.hide()
                    activity?.showToast(it.data.toString())
                    findNavController().navigateUp()
                }
            }
        }
    }
    private fun enableUI(isDisable: Boolean = false) {
        binding!!.noteTitleEt.isEnabled = isDisable
        binding!!.noteBodyEt.isEnabled = isDisable
    }
    private fun loadColorSpinner() {
        selectedColor = ColorList().defaultColor
        binding!!.colorSpinner.apply {
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
    private fun updateNote(view: View) {
        homeViewModel.updateFirebaseNote(getNote())
        Snackbar.make(mView, "Your note updated successfully", Snackbar.LENGTH_SHORT).show()
        mView.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
    }
    private fun deleteNote(view: View) {

        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note!")
            setMessage("Are you sure want to delete ${currentNote.noteTitle}!!")
            setPositiveButton("DELETE") { _, _ ->
                homeViewModel.deleteFirebaseNote(currentNote)
                view.findNavController().navigate(
                    UpdateNoteFragmentDirections.actionUpdateNoteFragmentToHomeFragment()
                )

            }
            setNegativeButton("CANCEL", null)
        }.create().show()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete_menu ->{
                deleteNote(mView)
            }
            R.id.update_menu ->{
                updateNote(mView)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun getNote(): Note {
        return Note(
          //  id = objNote?.id ?: "",
            id=currentNote.id,
            noteTitle = binding!!.noteTitleEt.text.toString(),
            noteBody = binding!!.noteBodyEt.text.toString(),
            //    images = getImageUrls(),
            date = Date(),
            colorObject = selectedColor
        ).apply { authViewModel.getSession { this.user_id = it?.id ?: "" } }
    }

}
package com.example.noteapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.NoteAdapter
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentHomeBinding
import com.example.noteapp.model.Note
import com.example.noteapp.ui.auth.AuthViewModel
import com.example.noteapp.util.Resource
import com.example.noteapp.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) ,SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val homeViewModel: HomeViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        setupRecyclerView()
        binding!!.addNoteFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }
        authViewModel.getSession {
            homeViewModel.getAllFirebaseNotes(it!!)
        }

    }

    private fun observer() {
        homeViewModel.note.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    binding!!.noNoteTv.visibility = View.VISIBLE
                    binding!!.homeFragmentRV.visibility = View.GONE
                    binding!!.progressBar.visibility=View.VISIBLE
                }
                is Resource.Success -> {
                    binding!!.noNoteTv.visibility = View.GONE
                    binding!!.progressBar.visibility=View.GONE
                    binding!!.homeFragmentRV.visibility = View.VISIBLE

                    noteAdapter.differ.submitList(response.data)
                    Log.d("data",response.data.toString())
                }
                is Resource.Error -> {
                    binding!!.noNoteTv.visibility = View.VISIBLE
                    binding!!.progressBar.visibility=View.GONE
                    binding!!.homeFragmentRV.visibility = View.GONE
                    activity?.showToast(response.message.toString())
                    Log.d("error",response.message.toString())
                }
            }
        }
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter()
        binding!!.homeFragmentRV.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = noteAdapter

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.home_menu, menu)
        val mMenuSearch = menu.findItem(R.id.app_bar_search).actionView as SearchView
        mMenuSearch.isQueryRefinementEnabled = true
        mMenuSearch.setOnQueryTextListener(this)


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
           // searchForNote(query)

        }
        return true

    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
           // searchForNote(newText)
        }
        return true
    }

    private fun searchForNote(query: String) {
        val searchQuery = "%$query%"
//        noteViewModel.searchForNote(searchQuery).observe(this)
//        {
//            noteAdapter.differ.submitList(it)
//        }

    }

}

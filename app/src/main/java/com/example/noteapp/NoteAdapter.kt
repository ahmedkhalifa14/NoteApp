package com.example.noteapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.CustomNoteLayoutBinding
import com.example.noteapp.ui.home.HomeFragmentDirections
import com.example.noteapp.model.Note
import java.text.SimpleDateFormat


class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    val sdf = SimpleDateFormat("dd MMM yy, hh:mm aaa")

    inner class NoteViewHolder(val itemBinding: CustomNoteLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            CustomNoteLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = differ.currentList[position]
        holder.itemBinding.customNoteTitleEt.text = note.noteTitle
        holder.itemBinding.customNoteBodyEt.text = note.noteBody
        holder.itemBinding.customNoteDate.text=sdf.format(note.date)
        holder.itemBinding.customNoteLayout.setBackgroundColor(Color.parseColor(note.colorObject!!.hexHash))
        holder.itemBinding.customNoteTitleEt.setTextColor(Color.parseColor(note.colorObject.contrastHexHash))
        holder.itemBinding.customNoteDate.setTextColor(Color.parseColor(note.colorObject.contrastHexHash))
        holder.itemBinding.customNoteBodyEt.setTextColor(Color.parseColor(note.colorObject.contrastHexHash))
        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToUpdateNoteFragment(note)
            it.findNavController().navigate(action)
        }
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Note) -> Unit)? = null
    fun setOnItemClickListener(listener: (Note) -> Unit) {
        onItemClickListener = listener
    }


}
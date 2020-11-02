package com.example.grammarengpro.ui.notebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grammarengpro.R
import com.example.grammarengpro.base.OnItemClick
import com.example.grammarengpro.data.model.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var listNote = mutableListOf<Note>()
    var onClickItem: OnItemClick<Note>? = null

    fun replaceData(data: MutableList<Note>) {
        listNote.clear()
        listNote.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_note, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = listNote[position]
        holder.titleNote.text = note.title
        holder.itemView.setOnClickListener { onClickItem?.onClickItem(note) }
    }

    override fun getItemCount(): Int = listNote.size

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleNote: TextView = itemView.findViewById(R.id.textTitleNote)
    }

}
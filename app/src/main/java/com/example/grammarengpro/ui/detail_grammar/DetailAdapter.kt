package com.example.grammarengpro.ui.detail_grammar

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grammarengpro.R
import com.example.grammarengpro.data.model.DetailStructure

class DetailAdapter(
    private val listDetail: List<DetailStructure>
) :
    RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_struct, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val detail = listDetail[position]
        holder.textTitle.text = detail.title
        holder.textTitle.paintFlags =
            holder.textTitle.paintFlags or Paint.UNDERLINE_TEXT_FLAG or Paint.FAKE_BOLD_TEXT_FLAG

        detail.listDetail?.let {
            var contentDetail = ""
            for (i in it) {
                contentDetail += i + "\n"
            }
            holder.textContentDetail.text = contentDetail
        }

        detail.listExample?.let {
            var contentExample = ""
            for (i in it) {
                contentExample += i + "\n"
            }
            holder.textContentExample.text = contentExample
        }

        detail.listNote?.let {
            var contentNote = ""
            for (i in it) {
                contentNote += i + "\n"
            }
            holder.textContentNote.text = contentNote
        }
    }

    override fun getItemCount(): Int {
        return listDetail.size
    }

    inner class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.textTitleStruct)
        val textContentDetail: TextView = itemView.findViewById(R.id.textContentDetail)
        val textContentExample: TextView = itemView.findViewById(R.id.textContentExample)
        val textContentNote: TextView = itemView.findViewById(R.id.textContentNote)
    }
}
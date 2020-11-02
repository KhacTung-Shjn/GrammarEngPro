package com.example.grammarengpro.ui.practice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grammarengpro.R
import com.example.grammarengpro.base.OnItemClick
import com.example.grammarengpro.data.model.Practice
import com.example.grammarengpro.utils.CommonUtils
import com.example.grammarengpro.utils.CustomProgressBar

class PracticeAdapter : RecyclerView.Adapter<PracticeAdapter.PracticeViewHolder>() {

    var listPractice = mutableListOf<Practice>()
    var onClickItem: OnItemClick<Practice>? = null
    var listPoint = hashMapOf<String, String>()
    fun replaceData(items: MutableList<Practice>) {
        listPractice.clear()
        listPractice.addAll(items)
        notifyDataSetChanged()
    }

    fun replacePoint(items: HashMap<String, String>) {
        listPoint.clear()
        listPoint.putAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PracticeViewHolder {
        return PracticeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_practice, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PracticeViewHolder, position: Int) {
        val practice = listPractice[position]
        CommonUtils.loadImage(holder.imagePractice.context, practice.imageUrl, holder.imagePractice)
        holder.textTitlePractice.text = practice.title

        val point = listPoint[practice.idPractice]!!.split("/")[0]
        holder.progressBarPoint.setProgress(point.toInt())

        holder.itemView.setOnClickListener { onClickItem?.onClickItem(practice) }
    }

    override fun getItemCount(): Int = listPractice.size

    inner class PracticeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagePractice: ImageView = itemView.findViewById(R.id.imageItemPractice)
        val textTitlePractice: TextView = itemView.findViewById(R.id.textTitlePractice)
        val progressBarPoint: CustomProgressBar = itemView.findViewById(R.id.progressBarPoint)
    }
}
package com.example.grammarengpro.ui.more

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grammarengpro.R
import com.example.grammarengpro.base.OnItemClick
import com.example.grammarengpro.data.model.Time

class ScheduleAdapter : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    private var itemsSchedule = mutableListOf<Time>()
    var onclickItem: OnItemClick<Time>? = null

    fun replaceData(data: MutableList<Time>) {
        itemsSchedule.clear()
        itemsSchedule.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        return ScheduleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_schedule, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val schedule = itemsSchedule[position]
        holder.textTitleSchedule.text = schedule.title
        holder.itemView.setOnClickListener { onclickItem?.onClickItem(schedule) }
    }

    override fun getItemCount(): Int = itemsSchedule.size

    inner class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitleSchedule: TextView = itemView.findViewById(R.id.textTitleSchedule)
    }

}
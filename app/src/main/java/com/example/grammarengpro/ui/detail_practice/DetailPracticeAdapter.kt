package com.example.grammarengpro.ui.detail_practice

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grammarengpro.R
import com.example.grammarengpro.data.model.DetailExam

class DetailPracticeAdapter :
    RecyclerView.Adapter<DetailPracticeAdapter.DetailPracticeViewHolder>() {

    private val items = mutableListOf<DetailExam>()
    var swipeQuestion: SwipeQuestion? = null

    fun replaceData(data: MutableList<DetailExam>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailPracticeViewHolder {
        return DetailPracticeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_question, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DetailPracticeViewHolder, position: Int) {
        val question = items[position]
        holder.textNumberQuestion.text = question.numberQuestion
        holder.textContentQuestion.text = question.listAnswer!![0]
        holder.textAnswerOne.text = question.listAnswer!![1]
        holder.textAnswerTwo.text = question.listAnswer!![2]
        holder.textAnswerThree.text = question.listAnswer!![3]
        holder.textAnswerFour.text = question.listAnswer!![4]

        holder.textAnswerOne.setBackgroundResource(R.drawable.bg_answer)
        holder.textAnswerTwo.setBackgroundResource(R.drawable.bg_answer)
        holder.textAnswerThree.setBackgroundResource(R.drawable.bg_answer)
        holder.textAnswerFour.setBackgroundResource(R.drawable.bg_answer)

        holder.textAnswerOne.isSelected = false
        holder.textAnswerTwo.isSelected = false
        holder.textAnswerThree.isSelected = false
        holder.textAnswerFour.isSelected = false

        val resultPosition = question.listAnswer?.indexOf(question.result!!)
        var isCorrect = false

        holder.textAnswerOne.setOnClickListener {
            isCorrect = true
            holder.textAnswerTwo.isClickable = false
            holder.textAnswerThree.isClickable = false
            holder.textAnswerFour.isClickable = false

            holder.textAnswerOne.isSelected = true

            if (resultPosition != 1) {
                isCorrect = false
                holder.textAnswerOne.setBackgroundResource(R.drawable.bg_answer_false)
                when (resultPosition) {
                    2 -> {
                        holder.textAnswerTwo.isSelected = true
                    }
                    3 -> {
                        holder.textAnswerThree.isSelected = true
                    }
                    4 -> {
                        holder.textAnswerFour.isSelected = true
                    }
                }
            }

            Looper.myLooper()?.let {
                Handler(it).postDelayed({
                    swipeQuestion?.swipeQuestion(position, isCorrect)
                }, 1000)
            }
        }
        holder.textAnswerTwo.setOnClickListener {
            isCorrect = true
            holder.textAnswerOne.isClickable = false
            holder.textAnswerThree.isClickable = false
            holder.textAnswerFour.isClickable = false

            holder.textAnswerTwo.isSelected = true
            if (resultPosition != 2) {
                isCorrect = false
                holder.textAnswerTwo.setBackgroundResource(R.drawable.bg_answer_false)
                when (resultPosition) {
                    1 -> {
                        holder.textAnswerOne.isSelected = true
                    }
                    3 -> {
                        holder.textAnswerThree.isSelected = true
                    }
                    4 -> {
                        holder.textAnswerFour.isSelected = true
                    }
                }
            }
            Looper.myLooper()?.let {
                Handler(it).postDelayed({
                    swipeQuestion?.swipeQuestion(position, isCorrect)
                }, 1000)
            }
        }
        holder.textAnswerThree.setOnClickListener {
            isCorrect = true
            holder.textAnswerOne.isClickable = false
            holder.textAnswerTwo.isClickable = false
            holder.textAnswerFour.isClickable = false

            holder.textAnswerThree.isSelected = true
            if (resultPosition != 3) {
                isCorrect = false
                holder.textAnswerThree.setBackgroundResource(R.drawable.bg_answer_false)
                when (resultPosition) {
                    1 -> {
                        holder.textAnswerOne.isSelected = true
                    }
                    2 -> {
                        holder.textAnswerTwo.isSelected = true
                    }
                    4 -> {
                        holder.textAnswerFour.isSelected = true
                    }
                }
            }
            Looper.myLooper()?.let {
                Handler(it).postDelayed({
                    swipeQuestion?.swipeQuestion(position, isCorrect)
                }, 1000)
            }
        }
        holder.textAnswerFour.setOnClickListener {
            isCorrect = true
            holder.textAnswerOne.isClickable = false
            holder.textAnswerTwo.isClickable = false
            holder.textAnswerThree.isClickable = false

            holder.textAnswerFour.isSelected = true
            if (resultPosition != 4) {
                isCorrect = false
                holder.textAnswerFour.setBackgroundResource(R.drawable.bg_answer_false)
                when (resultPosition) {
                    1 -> {
                        holder.textAnswerOne.isSelected = true
                    }
                    2 -> {
                        holder.textAnswerTwo.isSelected = true
                    }
                    3 -> {
                        holder.textAnswerThree.isSelected = true
                    }
                }
            }
            Looper.myLooper()?.let {
                Handler(it).postDelayed({
                    swipeQuestion?.swipeQuestion(position, isCorrect)
                }, 1000)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class DetailPracticeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textNumberQuestion: TextView = itemView.findViewById(R.id.textNumberQuestion)
        val textContentQuestion: TextView = itemView.findViewById(R.id.textContentQuestion)
        val textAnswerOne: TextView = itemView.findViewById(R.id.textAnswerOne)
        val textAnswerTwo: TextView = itemView.findViewById(R.id.textAnswerTwo)
        val textAnswerThree: TextView = itemView.findViewById(R.id.textAnswerThree)
        val textAnswerFour: TextView = itemView.findViewById(R.id.textAnswerFour)
    }
}
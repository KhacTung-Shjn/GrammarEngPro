package com.example.grammarengpro.ui.grammar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grammarengpro.R
import com.example.grammarengpro.base.OnItemClick
import com.example.grammarengpro.data.model.Grammar
import com.example.grammarengpro.utils.CommonUtils
import de.hdodenhof.circleimageview.CircleImageView

class GrammarAdapter(var listTitleGrammar: List<Grammar>, val context: Context) :
    RecyclerView.Adapter<GrammarAdapter.GrammarViewHolder>() {

    private var onClickItem: OnItemClick<Grammar>? = null

    fun setOnClickItem(onClickItem: OnItemClick<Grammar>) {
        this.onClickItem = onClickItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrammarViewHolder {
        return GrammarViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_grammar, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GrammarViewHolder, position: Int) {
        val grammar: Grammar = listTitleGrammar[position]
        grammar?.let {
            CommonUtils.loadImage(context, it.image, holder.imageGrammar)
            holder.textTitle.text = it.title
            holder.itemView.setOnClickListener { onClickItem?.onClickItem(grammar) }
        }
    }

    override fun getItemCount(): Int {
        return listTitleGrammar.size
    }

    inner class GrammarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageGrammar: CircleImageView = itemView.findViewById(R.id.imageIconGrammar)
        val textTitle: TextView = itemView.findViewById(R.id.textTitleGrammar)
    }

}
package com.example.grammarengpro.ui.grammar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grammarengpro.R
import com.example.grammarengpro.base.BaseFragment
import com.example.grammarengpro.base.OnItemClick
import com.example.grammarengpro.data.model.Grammar
import com.example.grammarengpro.ui.frame.FrameActivity

class GrammarFragment : BaseFragment(), GrammarFragmentContact.View, OnItemClick<Grammar> {

    private var grammarFragmentPresenter: GrammarFragmentPresenter? = null
    private var rcvListGrammar: RecyclerView? = null
    private var grammarAdapter: GrammarAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_grammar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcvListGrammar = view.findViewById(R.id.rcvListGrammar)
        rcvListGrammar?.setHasFixedSize(true)
        grammarFragmentPresenter?.loadListGrammar()
    }

    override fun initPresenter() {
        grammarFragmentPresenter = GrammarFragmentPresenter(this)
    }

    companion object {
        val TAG: String = GrammarFragment::class.java.simpleName
        private var instance: GrammarFragment? = null
        fun getInstance(): GrammarFragment =
            instance ?: synchronized(this) {
                instance ?: GrammarFragment().also { instance = it }
            }
    }

    override fun successGetListGrammar(listGrammar: MutableList<Grammar>) {
        listGrammar.let { listGrammar.reverse() }
        grammarAdapter = GrammarAdapter(listGrammar, context!!)
        grammarAdapter?.setOnClickItem(this)
        rcvListGrammar?.adapter = grammarAdapter
    }

    override fun onClickItem(t: Grammar) {
        context?.let {
            startActivity(FrameActivity.newInstanceDetailGrammar(it, t))
        }
    }

}

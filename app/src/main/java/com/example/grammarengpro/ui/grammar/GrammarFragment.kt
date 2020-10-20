package com.example.grammarengpro.ui.grammar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.grammarengpro.R
import com.example.grammarengpro.base.BaseFragment

class GrammarFragment : BaseFragment(), GrammarFragmentContact.View {

    private var grammarFragmentPresenter: GrammarFragmentPresenter? = null

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

}

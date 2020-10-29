package com.example.grammarengpro.ui.grammar

import com.example.grammarengpro.base.BasePresenter
import com.example.grammarengpro.base.BaseView
import com.example.grammarengpro.data.model.Grammar

interface GrammarFragmentContact {
    interface View : BaseView {
        fun successGetListGrammar(listGrammar: MutableList<Grammar>)
    }

    interface Presenter : BasePresenter {
        fun loadListGrammar()
    }
}

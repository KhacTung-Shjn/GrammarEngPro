package com.example.grammarengpro.ui.detail_grammar

import com.example.grammarengpro.base.BasePresenter
import com.example.grammarengpro.base.BaseView
import com.example.grammarengpro.data.model.DetailStructure
import com.example.grammarengpro.data.model.Structure

interface DetailGrammarFragmentContact {
    interface View : BaseView {
        fun successGetListDetail(listDetail: MutableList<DetailStructure>)
    }

    interface Presenter : BasePresenter {
        fun getListStruct(idGrammar: String?)
    }
}

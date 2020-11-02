package com.example.grammarengpro.ui.practice

import com.example.grammarengpro.base.BasePresenter
import com.example.grammarengpro.base.BaseView
import com.example.grammarengpro.data.model.Practice

interface PracticeFragmentContact {
    interface View : BaseView {
        fun successGetListPractice(listPractice: MutableList<Practice>)

    }

    interface Presenter : BasePresenter {
        fun getListPractice()
    }
}

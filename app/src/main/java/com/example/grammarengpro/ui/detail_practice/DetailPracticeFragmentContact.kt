package com.example.grammarengpro.ui.detail_practice

import com.example.grammarengpro.base.BasePresenter
import com.example.grammarengpro.base.BaseView
import com.example.grammarengpro.data.model.DetailExam

interface DetailPracticeFragmentContact {
    interface View : BaseView {
        fun successGetListExam(listExam: MutableList<DetailExam>?)

    }

    interface Presenter : BasePresenter {
        fun getListQuestion(idPractice: String?)
    }
}

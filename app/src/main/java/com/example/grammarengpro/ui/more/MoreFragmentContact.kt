package com.example.grammarengpro.ui.more

import com.example.grammarengpro.base.BasePresenter
import com.example.grammarengpro.base.BaseView

interface MoreFragmentContact {
    interface View : BaseView {
        fun successListTime(listTime: MutableList<String>)

    }

    interface Presenter : BasePresenter {
        fun scheduling(itemsChooseTimeFree: MutableList<Int>)

    }
}

package com.example.grammarengpro.ui.notebook

import com.example.grammarengpro.base.BasePresenter
import com.example.grammarengpro.base.BaseView
import com.example.grammarengpro.data.model.Note

interface NotebookFragmentContact {
    interface View : BaseView {
        fun successGetListNote(listNote: MutableList<Note>)
        fun addSuccess()
        fun removeSuccess()
        fun editSuccess()

    }

    interface Presenter : BasePresenter {
        fun getListNote(deviceID: String?)
        fun addNote(title: String, content: String, deviceID: String?)
        fun removeNote(t: Note)
        fun editNote(t: Note, title: String, content: String)
    }
}

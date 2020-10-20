package com.example.grammarengpro.ui.notebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.grammarengpro.R
import com.example.grammarengpro.base.BaseFragment

class NotebookFragment : BaseFragment(), NotebookFragmentContact.View {

    private var notebookFragmentPresenter: NotebookFragmentPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notebook, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initPresenter() {
        notebookFragmentPresenter = NotebookFragmentPresenter(this)
    }

    companion object {
        val TAG: String = NotebookFragment::class.java.simpleName
        private var instance: NotebookFragment? = null
        fun getInstance(): NotebookFragment =
            instance ?: synchronized(this) {
                instance ?: NotebookFragment().also { instance = it }
            }
    }

}

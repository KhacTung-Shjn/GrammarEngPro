package com.example.grammarengpro.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.grammarengpro.R
import com.example.grammarengpro.base.BaseFragment

class MoreFragment : BaseFragment(), MoreFragmentContact.View {

    private var moreFragmentPresenter: MoreFragmentPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initPresenter() {
        moreFragmentPresenter = MoreFragmentPresenter(this)
    }

    companion object {
        val TAG: String = MoreFragment::class.java.simpleName
        private var instance: MoreFragment? = null
        fun getInstance(): MoreFragment =
            instance ?: synchronized(this) {
                instance ?: MoreFragment().also { instance = it }
            }
    }

}

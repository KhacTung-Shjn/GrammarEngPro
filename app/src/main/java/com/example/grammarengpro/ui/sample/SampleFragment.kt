package com.example.grammarengpro.ui.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.grammarengpro.R
import com.example.grammarengpro.base.BaseFragment

class SampleFragment : BaseFragment(), SampleFragmentContact.View {

    private var presenter: SampleFragmentPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_sample, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initPresenter() {
        presenter = SampleFragmentPresenter(this)
    }

    companion object {
        val TAG: String = SampleFragment::class.java.simpleName
        private var instance: SampleFragment? = null
        fun getInstance(): SampleFragment =
            instance ?: synchronized(this) {
                instance ?: SampleFragment().also { instance = it }
            }
    }

}

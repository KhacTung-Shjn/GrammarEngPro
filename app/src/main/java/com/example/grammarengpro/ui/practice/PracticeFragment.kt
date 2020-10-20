package com.example.grammarengpro.ui.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.grammarengpro.R
import com.example.grammarengpro.base.BaseFragment

class PracticeFragment : BaseFragment(), PracticeFragmentContact.View {

    private var practiceFragmentPresenter: PracticeFragmentPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_practice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initPresenter() {
        practiceFragmentPresenter = PracticeFragmentPresenter(this)
    }

    companion object {
        val TAG: String = PracticeFragment::class.java.simpleName
        private var instance: PracticeFragment? = null
        fun getInstance(): PracticeFragment =
            instance ?: synchronized(this) {
                instance ?: PracticeFragment().also { instance = it }
            }
    }

}

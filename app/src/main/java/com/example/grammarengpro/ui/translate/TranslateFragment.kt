package com.example.grammarengpro.ui.translate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.grammarengpro.R
import com.example.grammarengpro.base.BaseFragment

class TranslateFragment : BaseFragment(), TranslateFragmentContact.View, View.OnClickListener {

    private var presenter: TranslateFragmentPresenter? = null
    private var textTranslate: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_translate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textTranslate = view.findViewById(R.id.textTranslate)
        textTranslate?.setOnClickListener(this)
    }

    override fun initPresenter() {
        presenter = TranslateFragmentPresenter(this)
    }

    override fun onClick(v: View?) {

    }

    companion object {
        val TAG: String = TranslateFragment::class.java.simpleName
        private var instance: TranslateFragment? = null
        fun getInstance(): TranslateFragment =
            instance ?: synchronized(this) {
                instance ?: TranslateFragment().also { instance = it }
            }
    }
}

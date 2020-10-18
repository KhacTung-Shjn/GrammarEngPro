package com.example.grammarengpro.ui.main

import android.os.Bundle
import com.example.grammarengpro.R
import com.example.grammarengpro.base.BaseActivity

class MainActivity : BaseActivity(), MainContact.View {

    private var presenter: MainContact.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun initPresenter() {
        presenter = MainPresenter(this)
    }

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

}

package com.example.grammarengpro.ui.sample

import android.os.Bundle
import com.example.grammarengpro.R
import com.example.grammarengpro.base.BaseActivity

class SampleActivity : BaseActivity(), SampleContact.View {

    private var presenter: SampleContact.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
    }

    override fun initPresenter() {
        presenter = SamplePresenter(this)
    }

    companion object {
        val TAG: String = SampleActivity::class.java.simpleName
    }

}

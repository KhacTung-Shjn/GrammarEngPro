package com.example.grammarengpro.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.grammarengpro.utils.showToast

abstract class BaseActivity : AppCompatActivity(), BaseView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
    }

    protected abstract fun initPresenter()

    override fun showMessage(idString: Int) {
        showMessage(getString(idString))
    }

    override fun showMessage(str: String) {
        baseContext.showToast(str)
    }

}

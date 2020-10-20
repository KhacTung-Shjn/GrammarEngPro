package com.example.grammarengpro.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.grammarengpro.utils.showToast

abstract class BaseFragment : Fragment(), BaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
    }

    protected abstract fun initPresenter()

    override fun showMessage(idString: Int) {
        showMessage(getString(idString))
    }

    override fun showMessage(str: String) {
        context?.showToast(str)
    }

}

package com.example.grammarengpro.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.grammarengpro.R
import com.example.grammarengpro.base.BaseActivity
import com.example.grammarengpro.data.model.FragmentController
import com.example.grammarengpro.ui.grammar.GrammarFragment
import com.example.grammarengpro.ui.more.MoreFragment
import com.example.grammarengpro.ui.notebook.NotebookFragment
import com.example.grammarengpro.ui.practice.PracticeFragment
import com.example.grammarengpro.ui.translate.TranslateFragment
import com.example.grammarengpro.utils.AppUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainContact.View,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private var presenter: MainContact.Presenter? = null
    private var listFragmentController: MutableList<FragmentController> = mutableListOf()
    private var currentFragment: Fragment? = null
    private var navBottom: BottomNavigationView? = null
    private var textTitle: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()
    }

    private fun initView() {
        navBottom = findViewById(R.id.navBottom)
        textTitle = findViewById(R.id.textTitleMain)
        navBottom?.itemIconTintList = null
        navBottom!!.setOnNavigationItemSelectedListener(this)
    }

    private fun initData() {
        listFragmentController.add(
            FragmentController(
                GrammarFragment.getInstance(),
                GrammarFragment.TAG
            )
        )
        listFragmentController.add(
            FragmentController(
                TranslateFragment.getInstance(),
                TranslateFragment.TAG
            )
        )
        listFragmentController.add(
            FragmentController(
                PracticeFragment.getInstance(),
                PracticeFragment.TAG
            )
        )
        listFragmentController.add(
            FragmentController(
                NotebookFragment.getInstance(),
                NotebookFragment.TAG
            )
        )
        listFragmentController.add(FragmentController(MoreFragment.getInstance(), MoreFragment.TAG))

        textTitle?.text = getString(R.string.title_grammar)
        currentFragment = AppUtils.addFragmentToActivity(
            supportFragmentManager,
            listFragmentController,
            R.id.frameMain,
            0
        )
    }

    override fun initPresenter() {
        presenter = MainPresenter(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var maybeActive = -1
        var title = ""
        when (item.itemId) {
            R.id.menuGrammar -> {
                imageAdd.visibility = View.INVISIBLE
                maybeActive = 0
                title = getString(R.string.title_grammar)
            }
            R.id.menuTranslate -> {
                imageAdd.visibility = View.INVISIBLE
                maybeActive = 1
                title = getString(R.string.title_translate)
            }
            R.id.menuPractice -> {
                imageAdd.visibility = View.INVISIBLE
                maybeActive = 2
                title = getString(R.string.title_practice)
            }
            R.id.menuNotebook -> {
                imageAdd.visibility = View.VISIBLE
                maybeActive = 3
                title = getString(R.string.title_notebook)
            }
            R.id.menuMore -> {
                imageAdd.visibility = View.INVISIBLE
                maybeActive = 4
                title = getString(R.string.title_more)
            }
        }
        if (maybeActive != -1) {
            textTitle?.text = title
            currentFragment?.let {
                currentFragment = AppUtils.switchFragmentActivity(
                    supportFragmentManager,
                    it,
                    listFragmentController[maybeActive].fragment
                )
            }
            return true
        }
        return false
    }

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

}

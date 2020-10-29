package com.example.grammarengpro.ui.frame

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.grammarengpro.R
import com.example.grammarengpro.base.BaseActivity
import com.example.grammarengpro.data.model.FragmentController
import com.example.grammarengpro.data.model.Grammar
import com.example.grammarengpro.ui.detail_grammar.DetailGrammarFragment
import com.example.grammarengpro.utils.AppUtils
import com.example.grammarengpro.utils.Const
import kotlinx.android.synthetic.main.activity_frame.*

class FrameActivity : BaseActivity(), FrameContact.View {

    private var presenter: FrameContact.Presenter? = null
    private var direct = ""
    private var asyncTaskExecutor: AsyncTaskExecutor? = null
    private var grammar: Grammar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)

        val intent = intent
        if (intent != null) {
            direct = intent.getStringExtra(Const.DIRECT_VERIFY).toString()
            grammar = intent.getParcelableExtra(Const.KEY_GRAMMAR)
        }

        asyncTaskExecutor = AsyncTaskExecutor()
        asyncTaskExecutor!!.execute()
    }

    override fun initPresenter() {
        presenter = FramePresenter(this)
    }

    @SuppressLint("StaticFieldLeak")
    inner class AsyncTaskExecutor :
        AsyncTask<Unit?, Unit?, FragmentController?>() {
        override fun doInBackground(vararg params: Unit?): FragmentController? {
            var title: String = ""
            var fragment: Fragment? = null
            var TAG: String = ""

            when (direct) {
                Const.DIRECT_VERIFY -> {}
                Const.DIRECT_DETAIL_GRAMMAR -> {
                    title = getString(R.string.title_detail_grammar)
                    grammar?.let {
                        fragment = DetailGrammarFragment.getInstance(it)
                    }
                    TAG = DetailGrammarFragment.TAG
                }
            }
            if (fragment != null) {
                textTitleFrame.text = title
                return FragmentController(fragment!!, TAG)
            }
            return null
        }

        override fun onPostExecute(result: FragmentController?) {
            super.onPostExecute(result)
            result?.let {
                AppUtils.replaceFragment(
                    supportFragmentManager,
                    R.id.frFrame,
                    it.fragment,
                    false,
                    it.tag
                )
            }
        }
    }


    //    public void onClickBack() {
    //        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
    //            getSupportFragmentManager().popBackStackImmediate();
    //        } else {
    //            finish();
    //        }
    //    }
    override fun onDestroy() {
        asyncTaskExecutor?.cancel(true)
        clearActivity(this, R.id.rootFrame)
        super.onDestroy()
    }

    companion object {
        fun newInstanceDetailGrammar(context: Context, grammar: Grammar?): Intent {
            val intent = Intent(context, FrameActivity::class.java)
            intent.putExtra(Const.DIRECT_VERIFY, Const.DIRECT_DETAIL_GRAMMAR)
            intent.putExtra(Const.KEY_GRAMMAR, grammar)
            return intent
        }
    }
}
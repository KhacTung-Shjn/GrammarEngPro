package com.example.grammarengpro.base

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.grammarengpro.MainApp
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

    override fun onResume() {
        MainApp.setCurrentActivity(this)
        super.onResume()
    }

    override fun onDestroy() {
        val currentActivity = MainApp.getCurrentActivity()
        if (this == currentActivity) {
            MainApp?.setCurrentActivity(null!!)
        }
        super.onDestroy()
    }

    @Synchronized
    open fun clearActivity(
        activity: Activity,
        rootLayout: Int
    ) {
        try {
            val view = activity.findViewById<View>(rootLayout)
            if (view != null) {
                unbindViewReferences(view)
                if (view is ViewGroup) unbindViewGroupReferences(view as ViewGroup)
            }
            System.gc()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    protected open fun unbindViewGroupReferences(viewGroup: ViewGroup) {
        val nrOfChildren = viewGroup.childCount
        for (i in 0 until nrOfChildren) {
            val view = viewGroup.getChildAt(i)
            unbindViewReferences(view)
            if (view is ViewGroup) unbindViewGroupReferences(view)
        }
        try {
            viewGroup.removeAllViews()
        } catch (mayHappen: Throwable) {
            mayHappen.printStackTrace()
        }
    }

    protected open fun unbindViewReferences(view: View) {
        try {
            view.setOnClickListener(null)
        } catch (mayHappen: Throwable) {
            mayHappen.printStackTrace()
        }
        try {
            view.setOnCreateContextMenuListener(null)
        } catch (mayHappen: Throwable) {
            mayHappen.printStackTrace()
        }
        try {
            view.onFocusChangeListener = null
        } catch (mayHappen: Throwable) {
            mayHappen.printStackTrace()
        }
        try {
            view.setOnKeyListener(null)
        } catch (mayHappen: Throwable) {
            mayHappen.printStackTrace()
        }
        try {
            view.setOnLongClickListener(null)
        } catch (mayHappen: Throwable) {
            mayHappen.printStackTrace()
        }
        try {
            view.setOnClickListener(null)
        } catch (mayHappen: Throwable) {
            mayHappen.printStackTrace()
        }
        setNullView(view)
        if (view is ImageView) {
            setNullImageView(view as ImageView)
        }
        if (view is WebView) {
            view.destroy()
        }
        if (view is Button) {
            view.setBackgroundResource(0)
        }
        if (view is RelativeLayout) {
            val rl = view
            rl.destroyDrawingCache()
            rl.setBackgroundResource(0)
        }
        if (view is LinearLayout) {
            val ln = view
            ln.destroyDrawingCache()
            ln.setBackgroundResource(0)
        }
        if (view is ListView) {
            view.destroyDrawingCache()
        }
        if (view is ConstraintLayout) {
            val constraintLayout = view
            constraintLayout.destroyDrawingCache()
            constraintLayout.setBackgroundResource(0)
        }
    }

    protected open fun setNullView(view: View?) {
        try {
            if (view != null) {
                view.setBackgroundDrawable(null)
                view.destroyDrawingCache()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected open fun setNullImageView(imgView: ImageView?) {
        try {
            if (imgView != null) {
                if (imgView.drawingCache != null
                    && !imgView.drawingCache.isRecycled
                ) {
                    imgView.drawingCache.recycle()
                }
                imgView.destroyDrawingCache()
                imgView.setBackgroundDrawable(null)
                imgView.setImageBitmap(null)
                imgView.setImageDrawable(null)
                imgView.setBackgroundResource(0)
                imgView.setImageResource(0)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}

package com.example.grammarengpro.ui.practice

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.grammarengpro.R
import com.example.grammarengpro.base.BaseFragment
import com.example.grammarengpro.base.OnItemClick
import com.example.grammarengpro.data.model.Practice
import com.example.grammarengpro.data.source.local.preference.PreferencesHelperImpl
import com.example.grammarengpro.ui.frame.FrameActivity
import com.example.grammarengpro.utils.Const
import kotlinx.android.synthetic.main.fragment_practice.*

class PracticeFragment : BaseFragment(), PracticeFragmentContact.View, OnItemClick<Practice> {

    private var practiceFragmentPresenter: PracticeFragmentPresenter? = null
    private val prefsHelper: PreferencesHelperImpl by lazy {
        PreferencesHelperImpl.getInstance(context?.getSharedPreferences(TAG, MODE_PRIVATE)!!)
    }
    private var listPoint: HashMap<String, String> = hashMapOf()
    private var isCheckListPoint = false
    private val adapter = PracticeAdapter()

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
        rcvListPractice.setHasFixedSize(true)
        practiceFragmentPresenter?.getListPractice()
        prefsHelper.getListPointPractice()?.let {
            listPoint = it
        }

    }

    override fun initPresenter() {
        practiceFragmentPresenter = PracticeFragmentPresenter(this)
    }

    override fun successGetListPractice(listPractice: MutableList<Practice>) {
        listPractice.reverse()

        for (item in listPractice) {
            if (!listPoint.containsKey(item.idPractice)) {
                isCheckListPoint = true
                listPoint[item.idPractice!!] = "0/20"
            }
        }

        if (isCheckListPoint) {
            prefsHelper.setListPointPractice(listPoint)
        }

        adapter.replaceData(listPractice)
        adapter.replacePoint(listPoint)
        adapter.onClickItem = this
        rcvListPractice.adapter = adapter
    }

    override fun onClickItem(t: Practice) {
        context?.let {
            startActivityForResult(
                FrameActivity.newInstanceDetailPractice(it, t.idPractice,0),
                Const.REQUEST_PRACTICE
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Const.REQUEST_PRACTICE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val idPractice = data.getStringExtra(Const.KEY_ID_PRACTICE)
                    val point = data.getIntExtra(Const.KEY_POINT_PRACTICE, 0)
                    listPoint.replace(idPractice!!, "$point/20")
                    prefsHelper.setListPointPractice(listPoint)
                    adapter.replacePoint(prefsHelper.getListPointPractice())
                    adapter.notifyDataSetChanged()
                }
            }
        }
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

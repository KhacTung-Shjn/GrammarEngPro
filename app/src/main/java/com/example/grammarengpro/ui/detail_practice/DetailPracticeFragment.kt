package com.example.grammarengpro.ui.detail_practice

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.example.grammarengpro.R
import com.example.grammarengpro.base.BaseFragment
import com.example.grammarengpro.data.model.DetailExam
import com.example.grammarengpro.utils.Const
import kotlinx.android.synthetic.main.fragment_detail_practice.*
import java.util.*

class DetailPracticeFragment : BaseFragment(), DetailPracticeFragmentContact.View,
    View.OnTouchListener, SwipeQuestion, View.OnClickListener {

    private var presenter: DetailPracticeFragmentPresenter? = null
    private var idPractice: String? = null
    private var listExam: MutableList<DetailExam> = mutableListOf()
    private var point: Int = 0
    private var dialogExit: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_practice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            idPractice = it.getString(Const.KEY_ID_PRACTICE)
            point = it.getInt(Const.KEY_POINT_PRACTICE)
        }
        activity?.let {
            it.findViewById<ImageView>(R.id.imageBack).setOnClickListener(this)
        }
        presenter?.getListQuestion(idPractice)
        rcvListQuestion.setOnTouchListener(this)
    }

    override fun initPresenter() {
        presenter = DetailPracticeFragmentPresenter(this)
    }

    override fun successGetListExam(listExam: MutableList<DetailExam>?) {
        listExam?.reverse()
        this.listExam = listExam!!
        rcvListQuestion.setHasFixedSize(true)
        val adapter = DetailPracticeAdapter()
        adapter.replaceData(listExam)
        adapter.swipeQuestion = this
        rcvListQuestion.adapter = adapter
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return true
    }

    override fun swipeQuestion(position: Int, isCorrect: Boolean) {
        if (isCorrect) point++
        if (position < this.listExam.size)
            rcvListQuestion.scrollToPosition(position + 1)
        if (position == this.listExam.size - 1) {
            val intent = Intent()
            intent.putExtra(Const.KEY_ID_PRACTICE, idPractice)
            intent.putExtra(Const.KEY_POINT_PRACTICE, point)
            activity?.setResult(Activity.RESULT_OK, intent)
            activity?.finish()
        }
    }

    override fun onClick(v: View?) {
        if (activity != null && context != null) {
            val builder = AlertDialog.Builder(context!!)
            val mView = layoutInflater.inflate(R.layout.dialog_exit_exam, null)
            builder.setView(mView)
            dialogExit = builder.create()
            Objects.requireNonNull(dialogExit!!.window)
                ?.setBackgroundDrawable(
                    ColorDrawable(Color.TRANSPARENT)
                )
            mView.findViewById<Button>(R.id.btnYes).setOnClickListener {
                activity?.finish()
            }
            mView.findViewById<Button>(R.id.btnNo).setOnClickListener {
                dialogExit?.dismiss()
            }
            dialogExit?.setCancelable(true)
            dialogExit?.show()
        }
    }

    override fun onDetach() {
        super.onDetach()
        dialogExit?.let {
            it.dismiss()
        }
    }

    companion object {
        val TAG: String = DetailPracticeFragment::class.java.simpleName
        private var instance: DetailPracticeFragment? = null
        fun getInstance(idPractice: String, point: Int): DetailPracticeFragment {
            instance ?: synchronized(this) {
                instance ?: DetailPracticeFragment().also { instance = it }
            }
            val bundle = Bundle()
            bundle.putString(Const.KEY_ID_PRACTICE, idPractice)
            bundle.putInt(Const.KEY_POINT_PRACTICE, point)
            instance!!.arguments = bundle
            return instance!!
        }
    }

}

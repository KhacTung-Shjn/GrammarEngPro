package com.example.grammarengpro.ui.more

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.view.isVisible
import com.example.grammarengpro.R
import com.example.grammarengpro.base.BaseFragment
import com.example.grammarengpro.base.OnItemClick
import com.example.grammarengpro.data.model.Time
import com.example.grammarengpro.data.source.local.preference.PreferencesHelperImpl
import com.example.grammarengpro.worker.AlarmWorker
import kotlinx.android.synthetic.main.fragment_more.*
import java.text.SimpleDateFormat
import java.util.*

class MoreFragment : BaseFragment(), MoreFragmentContact.View, View.OnClickListener,
    OnItemClick<Time> {

    private var moreFragmentPresenter: MoreFragmentPresenter? = null
    private var itemsChooseTimeFree = mutableListOf<Int>()
    private var adapter: ScheduleAdapter? = null
    private val prefsHelper: PreferencesHelperImpl by lazy {
        PreferencesHelperImpl.getInstance(
            context?.getSharedPreferences(
                MoreFragment.TAG,
                Context.MODE_PRIVATE
            )!!
        )
    }
    private var checkAlarm: IntArray = IntArray(7)
    private var currentMillis: Long = 0
    private var setupMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonTimeClick()
        buttonNotification.setOnClickListener(this)
        buttonCancelNotification.setOnClickListener(this)
        val listTimeChoose = prefsHelper.getListTimeChoose()
        currentMillis = Calendar.getInstance().timeInMillis
        textTime.text = prefsHelper.getTimeChoose()
        setCheckAlarm(prefsHelper.getCheckAlarm())
        if (prefsHelper.getOpenNotification()) {
            buttonCancelNotification.visibility = VISIBLE
            buttonNotification.visibility = GONE
        }

        if (listTimeChoose.size != 0) {
            for (i in listTimeChoose) {
                when (i) {
                    2 -> {
                        textT2.isSelected = true
                    }
                    3 -> {
                        textT3.isSelected = true
                    }
                    4 -> {
                        textT4.isSelected = true
                    }
                    5 -> {
                        textT5.isSelected = true
                    }
                    6 -> {
                        textT6.isSelected = true
                    }
                    7 -> {
                        textT7.isSelected = true
                    }
                    8 -> {
                        textCN.isSelected = true
                    }
                }
            }
            itemsChooseTimeFree = listTimeChoose
            moreFragmentPresenter?.scheduling(listTimeChoose)
        }
    }

    private fun setButtonTimeClick() {
        textT2.setOnClickListener(this)
        textT3.setOnClickListener(this)
        textT4.setOnClickListener(this)
        textT5.setOnClickListener(this)
        textT6.setOnClickListener(this)
        textT7.setOnClickListener(this)
        textCN.setOnClickListener(this)
        buttonRecommend.setOnClickListener(this)
    }

    override fun initPresenter() {
        moreFragmentPresenter = MoreFragmentPresenter(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.textT2 -> {
                textT2.isSelected = !textT2.isSelected
                if (textT2.isSelected) {
                    if (!itemsChooseTimeFree.contains(2)) {
                        itemsChooseTimeFree.add(2)
                    }
                } else {
                    if (itemsChooseTimeFree.contains(2)) {
                        itemsChooseTimeFree.remove(2)
                    }
                }
            }
            R.id.textT3 -> {
                textT3.isSelected = !textT3.isSelected
                if (textT3.isSelected) {
                    if (!itemsChooseTimeFree.contains(3)) {
                        itemsChooseTimeFree.add(3)
                    }
                } else {
                    if (itemsChooseTimeFree.contains(3)) {
                        itemsChooseTimeFree.remove(3)
                    }
                }
            }
            R.id.textT4 -> {
                textT4.isSelected = !textT4.isSelected
                if (textT4.isSelected) {
                    if (!itemsChooseTimeFree.contains(4)) {
                        itemsChooseTimeFree.add(4)
                    }
                } else {
                    if (itemsChooseTimeFree.contains(4)) {
                        itemsChooseTimeFree.remove(4)
                    }
                }
            }
            R.id.textT5 -> {
                textT5.isSelected = !textT5.isSelected
                if (textT5.isSelected) {
                    if (!itemsChooseTimeFree.contains(5)) {
                        itemsChooseTimeFree.add(5)
                    }
                } else {
                    if (itemsChooseTimeFree.contains(5)) {
                        itemsChooseTimeFree.remove(5)
                    }
                }
            }
            R.id.textT6 -> {
                textT6.isSelected = !textT6.isSelected
                if (textT6.isSelected) {
                    if (!itemsChooseTimeFree.contains(6)) {
                        itemsChooseTimeFree.add(6)
                    }
                } else {
                    if (itemsChooseTimeFree.contains(6)) {
                        itemsChooseTimeFree.remove(6)
                    }
                }
            }
            R.id.textT7 -> {
                textT7.isSelected = !textT7.isSelected
                if (textT7.isSelected) {
                    if (!itemsChooseTimeFree.contains(7)) {
                        itemsChooseTimeFree.add(7)
                    }
                } else {
                    if (itemsChooseTimeFree.contains(7)) {
                        itemsChooseTimeFree.remove(7)
                    }
                }
            }
            R.id.textCN -> {
                textCN.isSelected = !textCN.isSelected
                if (textCN.isSelected) {
                    if (!itemsChooseTimeFree.contains(8)) {
                        itemsChooseTimeFree.add(8)
                    }
                } else {
                    if (itemsChooseTimeFree.contains(8)) {
                        itemsChooseTimeFree.remove(8)
                    }
                }
            }
            R.id.buttonRecommend -> {
                if (itemsChooseTimeFree.size == 0) {
                    showMessage(R.string.msg_empty_free_time)
                    return
                }
                prefsHelper.setListTimeChoose(itemsChooseTimeFree)
                itemsChooseTimeFree.sort()
                moreFragmentPresenter?.scheduling(itemsChooseTimeFree)
            }
            R.id.buttonNotification -> {
                context?.let {
                    if (textTime.text.toString() != ".....") {
                        AlarmWorker.startScheduleReminder(
                            activity,
                            checkAlarm,
                            currentMillis,
                            setupMillis
                        )
                        prefsHelper.setOpenNotification(true)
                        buttonNotification.visibility = View.GONE
                        buttonCancelNotification.visibility = View.VISIBLE
                    } else {
                        showMessage(R.string.msg_error_choose_time)
                    }
                }
            }
            R.id.buttonCancelNotification -> {
                activity?.let {
                    AlarmWorker.stopScheduleReminder(it)
                }
                textTime.text = "....."
                textT2.isSelected = false
                textT3.isSelected = false
                textT4.isSelected = false
                textT5.isSelected = false
                textT6.isSelected = false
                textT7.isSelected = false
                textCN.isSelected = false

                itemsChooseTimeFree.clear()
                if (adapter != null) {
                    adapter?.replaceData(mutableListOf())
                    adapter?.notifyDataSetChanged()
                }
                prefsHelper.setOpenNotification(false)
                buttonNotification.visibility = View.VISIBLE
                buttonCancelNotification.visibility = View.GONE
            }
        }
    }

    override fun successListTime(listTime: MutableList<String>) {
        val listSchedule = mutableListOf<Time>()
        for (item in listTime) {
            listSchedule.add(Time(replaceTime(item), 0L, false))
        }

        adapter = ScheduleAdapter()
        adapter!!.replaceData(listSchedule)
        adapter!!.onclickItem = this
        rcvListSchedule.setHasFixedSize(true)
        rcvListSchedule.adapter = adapter
    }

    private fun replaceTime(item: String): String {
        val tmp = item.split("")
        var time = ""
        for (i in tmp) {
            time += sloveTime(i) + " "
        }
        return time.trim()
    }

    private fun sloveTime(i: String): String {
        when (i) {
            "2" -> {
                return "T2"
            }
            "3" -> {
                return "T3"
            }
            "4" -> {
                return "T4"
            }
            "5" -> {
                return "T5"
            }
            "6" -> {
                return "T6"
            }
            "7" -> {
                return "T7"
            }
            "8" -> {
                return "CN"
            }
        }
        return ""
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onClickItem(t: Time) {
        val timeDialogPickerDialog =
            TimePickerDialog(
                context,
                { view, hourOfDay, minute ->
                    checkAlarm = IntArray(7)
                    val cal = Calendar.getInstance()
                    cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    cal.set(Calendar.MINUTE, minute)
                    val chooseTime = SimpleDateFormat("HH:mm").format(cal.time)
                    textTime.text = "${t.title}, $chooseTime"
                    prefsHelper.setCheckAlarm(t.title)
                    prefsHelper.setTimeChoose(textTime.text.toString())
                    setCheckAlarm(t.title)
                    setupMillis = cal.timeInMillis
                    if (buttonCancelNotification.isVisible) {
                        buttonCancelNotification.visibility = GONE
                        buttonNotification.visibility = VISIBLE
                    }
                },
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true
            ).show()
    }

    private fun setCheckAlarm(title: String) {
        if (title != "") {
            if (title.contains("T2")) checkAlarm!![1] = 99
            if (title.contains("T3")) checkAlarm!![2] = 99
            if (title.contains("T4")) checkAlarm!![3] = 99
            if (title.contains("T5")) checkAlarm!![4] = 99
            if (title.contains("T6")) checkAlarm!![5] = 99
            if (title.contains("T7")) checkAlarm!![6] = 99
            if (title.contains("CN")) checkAlarm!![0] = 99
        }
    }

    companion object {
        val TAG: String = MoreFragment::class.java.simpleName
        private var instance: MoreFragment? = null
        fun getInstance(): MoreFragment =
            instance ?: synchronized(this) {
                instance ?: MoreFragment().also { instance = it }
            }
    }

}

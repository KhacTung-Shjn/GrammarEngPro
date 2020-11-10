package com.example.grammarengpro.ui.more

import android.util.Log
import com.example.grammarengpro.data.model.Time

class MoreFragmentPresenter(val view: MoreFragmentContact.View) :
    MoreFragmentContact.Presenter {

    private var listNP: MutableList<Int>? = null
    private var listTime: MutableList<String>? = null

    override fun scheduling(itemsChooseTimeFree: MutableList<Int>) {
        val listSize = itemsChooseTimeFree.size
        listTime = mutableListOf()
        listNP = mutableListOf()
        for (i in 0 until listSize) {
            listNP!!.add(0)
        }
        sinhNP(0, listSize, itemsChooseTimeFree)

        listTime!!.sort()

        view.successListTime(listTime!!)
    }

    private fun sinhNP(i: Int, listSize: Int, itemsChooseTimeFree: MutableList<Int>) {
        for (j in 0..1) {
            listNP!![i] = j
            if (i == listSize - 1) {
                var str = ""
                for (k in 0 until listNP!!.size) {
                    if (listNP!![k] == 1) {
                        str += itemsChooseTimeFree[k]
                    }
                }
                if (str != "") {
                    listTime!!.add(str)
                }
            } else {
                sinhNP(i + 1, listSize, itemsChooseTimeFree)
            }
        }
    }
}
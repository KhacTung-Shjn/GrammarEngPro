package com.example.grammarengpro.data.source.local.preference

interface PreferencesHelper {

    fun setListPointPractice(listPoint: HashMap<String, String>)

    fun getListPointPractice(): HashMap<String, String>

    fun setListTimeChoose(itemsChooseTimeFree: MutableList<Int>)

    fun getListTimeChoose(): MutableList<Int>

    fun setOpenNotification(isOpen: Boolean)

    fun getOpenNotification(): Boolean

    fun setTimeChoose(string: String)

    fun getTimeChoose(): String

    fun setCheckAlarm(str: String)

    fun getCheckAlarm(): String
}

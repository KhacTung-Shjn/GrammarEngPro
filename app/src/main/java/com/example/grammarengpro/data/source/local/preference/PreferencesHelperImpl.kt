package com.example.grammarengpro.data.source.local.preference

import android.content.SharedPreferences
import android.text.TextUtils
import com.example.grammarengpro.utils.Const
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class PreferencesHelperImpl(private val sharedPreferences: SharedPreferences) : PreferencesHelper {

    companion object {
        private var instance: PreferencesHelperImpl? = null
        fun getInstance(sharedPreferences: SharedPreferences): PreferencesHelperImpl =
            instance ?: synchronized(this) {
                instance ?: PreferencesHelperImpl(sharedPreferences).also { instance = it }
            }
    }

    override fun setListPointPractice(listPoint: HashMap<String, String>) {
        val dataJson = Gson().toJson(listPoint)
        sharedPreferences.edit().putString(Const.PREFS_LIST_POINT_PRACTICE, dataJson).apply()
    }

    override fun getListPointPractice(): HashMap<String, String> {
        var listPoint: HashMap<String, String> = hashMapOf()
        val str = sharedPreferences.getString(Const.PREFS_LIST_POINT_PRACTICE, null)
        if (!TextUtils.isEmpty(str)) {
            val type = object : TypeToken<HashMap<String, String>>() {}.type
            listPoint = Gson().fromJson(str, type)
        }
        return listPoint
    }

    override fun setListTimeChoose(itemsChooseTimeFree: MutableList<Int>) {
        val dataJson = Gson().toJson(itemsChooseTimeFree)
        sharedPreferences.edit().putString(Const.PREFS_LIST_TIME_CHOOSE, dataJson).apply()
    }

    override fun getListTimeChoose(): MutableList<Int> {
        var listTimeChoose = mutableListOf<Int>()
        val str = sharedPreferences.getString(Const.PREFS_LIST_TIME_CHOOSE, null)
        if (!TextUtils.isEmpty(str)) {
            val type = object : TypeToken<MutableList<Int>>() {}.type
            listTimeChoose = Gson().fromJson(str, type)
        }
        return listTimeChoose
    }

    override fun setOpenNotification(isOpen: Boolean) {
        sharedPreferences.edit().putBoolean(Const.PREFS_OPEN_NOTIFICATION, isOpen).apply()
    }

    override fun getOpenNotification(): Boolean =
        sharedPreferences.getBoolean(Const.PREFS_OPEN_NOTIFICATION, false)

    override fun setTimeChoose(string: String) {
        sharedPreferences.edit().putString(Const.PREFS_TIME_CHOOSE, string).apply()
    }

    override fun getTimeChoose(): String =
        sharedPreferences.getString(Const.PREFS_TIME_CHOOSE, ".....").toString()

    override fun setCheckAlarm(str: String) {
        sharedPreferences.edit().putString(Const.PREFS_CHECK_ALARM, str).apply()
    }

    override fun getCheckAlarm(): String =
        sharedPreferences.getString(Const.PREFS_CHECK_ALARM, "").toString()
}

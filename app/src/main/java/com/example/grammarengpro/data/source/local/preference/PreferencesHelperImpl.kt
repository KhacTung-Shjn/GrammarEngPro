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
}

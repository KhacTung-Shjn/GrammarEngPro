package com.example.grammarengpro.data.source.local.preference

interface PreferencesHelper {

    fun setListPointPractice(listPoint: HashMap<String, String>)
    fun getListPointPractice(): HashMap<String, String>
}

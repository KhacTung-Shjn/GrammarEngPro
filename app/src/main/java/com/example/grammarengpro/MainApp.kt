package com.example.grammarengpro

import android.app.Application
import com.facebook.stetho.Stetho

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }

    companion object {
        val TAG: String = MainApp::class.java.simpleName
    }

}

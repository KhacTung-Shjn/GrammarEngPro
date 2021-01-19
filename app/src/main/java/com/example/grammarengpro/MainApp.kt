package com.example.grammarengpro

import android.app.Activity
import android.app.Application
import com.facebook.stetho.Stetho
import com.google.firebase.firestore.FirebaseFirestore

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        firebaseFirestore = FirebaseFirestore.getInstance()
    }


    companion object {
        val TAG: String = MainApp::class.java.simpleName
        private var instance: MainApp? = null
        private var firebaseFirestore: FirebaseFirestore? = null
        private var mCurrentActivity: Activity? = null
        fun getInstance(): MainApp = instance ?: synchronized(this) {
            instance ?: MainApp().also { instance = it }
        }

        fun getFirebaseFireStore(): FirebaseFirestore = firebaseFirestore ?: synchronized(this) {
            firebaseFirestore ?: FirebaseFirestore.getInstance().also { firebaseFirestore = it }
        }

        fun setCurrentActivity(currentActivity: Activity) {
            this.mCurrentActivity = currentActivity
        }

        fun getCurrentActivity(): Activity {
            this.mCurrentActivity?.let {
                return it
            }
            return null!!
        }
    }
}

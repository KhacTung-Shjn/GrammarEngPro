package com.example.grammarengpro

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

        fun getInstance(): MainApp = instance ?: synchronized(this) {
            instance ?: MainApp().also { instance = it }
        }

        fun getFirebaseFireStore(): FirebaseFirestore = firebaseFirestore ?: synchronized(this) {
            firebaseFirestore ?: FirebaseFirestore.getInstance().also { firebaseFirestore = it }
        }

    }

}

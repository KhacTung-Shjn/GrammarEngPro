package com.example.grammarengpro.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.grammarengpro.R
import com.example.grammarengpro.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Looper.myLooper()?.let {
            Handler(it).postDelayed({
                startActivity(Intent(baseContext, MainActivity::class.java))
            }, 1000)
        }
    }
}

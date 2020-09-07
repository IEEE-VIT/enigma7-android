package com.ieeevit.enigma7.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.view.auth.AuthActivity


class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({ startActivity(Intent(this, AuthActivity::class.java))
            finish()
        },SPLASH_TIME_OUT)
        
    }
}
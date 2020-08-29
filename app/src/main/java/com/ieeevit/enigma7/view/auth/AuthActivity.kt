package com.ieeevit.enigma7.view.auth


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ieeevit.enigma7.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        if (savedInstanceState == null) {
            val fragment =SignUpFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.container, fragment)
                .commit()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
    }
}
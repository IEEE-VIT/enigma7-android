package com.ieeevit.enigma7.view.auth


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.view.home.ProfileFragment

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        if (savedInstanceState == null) {
            val fragment =ProfileFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.container, fragment)
                .commit()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        actionBar?.hide()
    }
}
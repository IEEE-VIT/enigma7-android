package com.ieeevit.enigma7.view.timer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ieeevit.enigma7.R

class CountdownActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        if (savedInstanceState == null) {
            val fragment = CountdownFragment()
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
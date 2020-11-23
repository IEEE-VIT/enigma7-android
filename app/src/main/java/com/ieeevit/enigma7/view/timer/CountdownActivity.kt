package com.ieeevit.enigma7.view.timer

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ieeevit.enigma7.R
import kotlinx.android.synthetic.main.bottom_bar.*

class CountdownActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)
        if (savedInstanceState == null) {
            val fragment = CountdownFragment()
            supportFragmentManager.beginTransaction().add(R.id.container, fragment).commit()
        }
        game.setOnClickListener { Toast.makeText(applicationContext,"Enigma is not Live yet", Toast.LENGTH_SHORT).show() }
        leaderboard.setOnClickListener { Toast.makeText(applicationContext,"Enigma is not Live yet", Toast.LENGTH_SHORT).show() }
        story.setOnClickListener { Toast.makeText(applicationContext,"Enigma is not Live yet", Toast.LENGTH_SHORT).show() }
        profile.setOnClickListener { Toast.makeText(applicationContext,"Enigma is not Live yet", Toast.LENGTH_SHORT).show() }
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        actionBar?.hide()
    }
}
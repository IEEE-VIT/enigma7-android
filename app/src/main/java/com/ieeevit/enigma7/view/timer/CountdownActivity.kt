package com.ieeevit.enigma7.view.timer

import android.os.Bundle
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
        game.isEnabled=false
        leaderboard.isEnabled=false
        story.isEnabled=false
        profile.isEnabled=false
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        actionBar?.hide()
    }
}
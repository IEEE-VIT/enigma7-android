package com.ieeevit.enigma7.view.timer

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.view.BaseActivity
import kotlinx.android.synthetic.main.activity_count_down.*
import kotlinx.android.synthetic.main.bottom_bar.*

class CountdownActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)
        if (savedInstanceState == null) {
            val fragment = CountdownFragment()
            supportFragmentManager.beginTransaction().add(R.id.container, fragment).commit()
        }
        game.setOnClickListener { makeSnackBar() }
        leaderboard.setOnClickListener { makeSnackBar() }
        story.setOnClickListener { makeSnackBar() }
        profile.setOnClickListener { makeSnackBar() }
    }

    private fun makeSnackBar() {
        Snackbar.make(constraint, "Enigma is not Live", Snackbar.LENGTH_SHORT).show()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        actionBar?.hide()
    }
}
package com.ieeevit.enigma7.view.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.utils.PrefManager
import com.ieeevit.enigma7.view.home.ProfileFragment
import kotlinx.android.synthetic.main.bottom_bar.*


class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreference: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = PrefManager(applicationContext)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        if (sharedPreference.isFirstTimeInstruction()) {
            transaction(InstructionsFragment())
        } else {
            transaction(PlayFragment())
        }

        game.setOnClickListener { transaction(PlayFragment()) }
        leaderboard.setOnClickListener { transaction(LeaderboardFragment()) }
        story.setOnClickListener { transaction(StoryFragment()) }
        profile.setOnClickListener { transaction(ProfileFragment()) }
    }

    private fun transaction(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
            .replace(R.id.container, fragment)
            .commit()
    }
}
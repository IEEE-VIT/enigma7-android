package com.ieeevit.enigma7.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.utils.PrefManager
import com.ieeevit.enigma7.view.BaseActivity
import kotlinx.android.synthetic.main.bottom_bar.*


class MainActivity : BaseActivity() {
    private lateinit var sharedPreference: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreference = PrefManager(applicationContext)
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
        supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
            .replace(R.id.container, fragment)
            .commit()
    }
}
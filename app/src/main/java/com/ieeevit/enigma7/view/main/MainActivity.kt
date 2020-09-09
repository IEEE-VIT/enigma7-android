package com.ieeevit.enigma7.view.main

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.view.home.ProfileFragment
import kotlinx.android.synthetic.main.bottom_bar.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        transaction(InstructionsFragment())

        val bottomBar:RelativeLayout=findViewById(R.id.bottom_nav)

        game.setOnClickListener { transaction(PlayFragment()) }
        leaderboard.setOnClickListener { transaction(LeaderboardFragment()) }
        story.setOnClickListener { transaction(StoryFragment()) }
        profile.setOnClickListener { transaction(ProfileFragment()) }
    }

    private fun transaction(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}
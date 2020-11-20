package com.ieeevit.enigma7.view.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.utils.PrefManager
import com.ieeevit.enigma7.view.home.ProfileFragment
import com.ieeevit.enigma7.viewModel.PlayViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_bar.*


class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreference: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        val viewModel: PlayViewModel by lazy {
            val activity = requireNotNull(this) {
            }
            ViewModelProvider(this, PlayViewModel.Factory(activity.application))
                .get(PlayViewModel::class.java)
        }

        sharedPreference = PrefManager(applicationContext)

        viewModel.getStory(sharedPreference.getAuthCode()!!)
        viewModel.story.observe(this,{
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
                .replace(R.id.container, StorySnippetFragment(it.question_story.story_text))
                .commit()
        })

        progress_bar_main.visibility=View.GONE

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
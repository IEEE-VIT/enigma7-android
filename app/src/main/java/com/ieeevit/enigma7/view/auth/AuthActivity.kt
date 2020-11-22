package com.ieeevit.enigma7.view.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.databinding.ActivityAuthBinding
import com.ieeevit.enigma7.utils.PrefManager
import com.ieeevit.enigma7.view.main.MainActivity
import com.ieeevit.enigma7.view.timer.CountdownActivity
import kotlinx.android.synthetic.main.bottom_bar.view.*

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var sharedPreference: PrefManager
    override fun onStart() {
        super.onStart()
        if (sharedPreference.isLoggedIn() && sharedPreference.getUserStaus() == true && sharedPreference.isHuntStarted()){
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
        else if (sharedPreference.isLoggedIn() && sharedPreference.getUserStaus() == true) {
            startActivity(Intent(applicationContext, CountdownActivity::class.java))
        } else if (sharedPreference.isLoggedIn()) {
            navigateToProfileSetup()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = PrefManager(applicationContext)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)
        if (savedInstanceState == null) {
            val fragment = SignUpFragment()
            supportFragmentManager.beginTransaction().add(R.id.container, fragment).commit()
        }

        binding.include2.game.isEnabled=false
        binding.include2.leaderboard.isEnabled=false
        binding.include2.story.isEnabled=false
        binding.include2.profile.isEnabled=false

    }

    private fun navigateToProfileSetup() {
        val fragment = ProfileSetupFragment()
        supportFragmentManager.beginTransaction().add(R.id.container, fragment).commit()
    }
}
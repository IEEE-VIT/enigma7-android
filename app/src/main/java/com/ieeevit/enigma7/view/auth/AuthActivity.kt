package com.ieeevit.enigma7.view.auth

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.databinding.ActivityAuthBinding
import com.ieeevit.enigma7.utils.PrefManager
import com.ieeevit.enigma7.view.BaseActivity
import com.ieeevit.enigma7.view.main.MainActivity
import com.ieeevit.enigma7.view.timer.CountdownActivity
import kotlinx.android.synthetic.main.activity_count_down.*
import kotlinx.android.synthetic.main.bottom_bar.view.*

class AuthActivity : BaseActivity() {
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

        binding.include2.game.setOnClickListener { Snackbar.make(constraint,"Please Login",Snackbar.LENGTH_SHORT).show() }
        binding.include2.leaderboard.setOnClickListener { Snackbar.make(constraint,"Please Login",Snackbar.LENGTH_SHORT).show() }
        binding.include2.story.setOnClickListener { Snackbar.make(constraint,"Please Login",Snackbar.LENGTH_SHORT).show() }
        binding.include2.profile.setOnClickListener { Snackbar.make(constraint,"Please Login",Snackbar.LENGTH_SHORT).show() }

    }

    private fun navigateToProfileSetup() {
        val fragment = ProfileSetupFragment()
        supportFragmentManager.beginTransaction().add(R.id.container, fragment).commit()
    }
}
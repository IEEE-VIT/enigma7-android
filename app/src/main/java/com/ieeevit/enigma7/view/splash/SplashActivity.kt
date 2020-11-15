package com.ieeevit.enigma7.view.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.databinding.ActivitySplashBinding
import com.ieeevit.enigma7.utils.PrefManager
import com.ieeevit.enigma7.view.auth.AuthActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private var shortAnimationDuration: Int = 0
    private lateinit var sharedPreference: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = PrefManager(applicationContext)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        if (!sharedPreference.isFirstTimeLaunch()) {
            navigateToProfileSetup()
        } else {
            initialize()
            shortAnimationDuration = resources.getInteger(android.R.integer.config_longAnimTime)
            Handler(Looper.getMainLooper()).postDelayed(
                { binding.container.crypticText.setText(R.string.anglur_cryptic) },
                800
            )
            binding.container.crypticText.apply {
                //fade in animation for the crypticText
                alpha = 0f
                visibility = View.VISIBLE
                animate().alpha(1f).setDuration(1500.toLong())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            binding.container.crypticText.setText(R.string.onlineCrypticHunt)
                            Handler(Looper.getMainLooper()).postDelayed({
                                if (sharedPreference.isLoggedIn()) {
                                    startIntent()
                                } else {
                                    binding.constraintLayout.setBackgroundResource(R.color.baseBackground)
                                    binding.include2.visibility = View.VISIBLE
                                    binding.container.constraintLayout.setBackgroundResource(R.drawable.fragment_back)
                                    binding.container.zeroOne.visibility = View.VISIBLE
                                    binding.container.zeroOne.animate()
                                        .translationYBy((1500).toFloat())
                                        .setDuration(400).setListener(
                                            object : AnimatorListenerAdapter() {
                                                override fun onAnimationEnd(animation: Animator) {
                                                    translateXY()
                                                    startIntent()
                                                    animate().setListener(null)
                                                }
                                            })
                                }

                            }, 250)
                        }
                    })
            }
        }

    }

    fun translateXY() {
        binding.container.googleSignup.visibility = View.VISIBLE
        binding.container.googleSignup.animate().translationYBy((-1500).toFloat()).duration = 400
        binding.container.enigma.animate().translationYBy((-373).toFloat()).duration = 400
        binding.container.crypticText.animate().translationYBy((-373).toFloat()).duration = 400
    }

    fun startIntent() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, AuthActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 400)
    }

    private fun initialize() {
        binding.container.crypticText.visibility = View.GONE
        binding.container.zeroOne.translationY = ((-1500).toFloat())
        binding.container.googleSignup.translationY = (1500.toFloat())
        binding.container.crypticText.setText(R.string.angular)
    }

    private fun navigateToProfileSetup() {
        startActivity(Intent(applicationContext, AuthActivity::class.java))
    }
}
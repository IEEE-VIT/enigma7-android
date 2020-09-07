package com.ieeevit.enigma7.view.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.databinding.ActivitySplashBinding
import com.ieeevit.enigma7.view.auth.AuthActivity


class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private var shortAnimationDuration: Int = 0
    private val SPLASH_TIME_OUT: Long = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.container.crypticText.visibility = View.GONE
        binding.container.zeroOne.translationY = ((-1500).toFloat())
        binding.container.googleSignup.translationY = (1500.toFloat())
        binding.container.instaSignup.translationY = (1500.toFloat())
        binding.container.crypticText.setText(R.string.angular)
        shortAnimationDuration = resources.getInteger(android.R.integer.config_longAnimTime)
        Handler().postDelayed(
            { binding.container.crypticText.setText(R.string.anglur_cryptic) },
            700
        )
        binding.container.crypticText.apply {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.
            animate()
                .alpha(1f)
                .setDuration(1500.toLong())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        binding.container.crypticText.setText(R.string.onlineCrypticHunt)
                        Handler().postDelayed({
                            binding.constraintLayout.setBackgroundResource(R.color.baseBackground)
                            binding.include2.visibility = View.VISIBLE
                            binding.container.constraintLayout.setBackgroundResource(R.drawable.fragment_back)
                            binding.container.zeroOne.visibility = View.VISIBLE
                            binding.container.zeroOne.animate().translationYBy((1500).toFloat())
                                .setDuration(400).setListener(
                                    object : AnimatorListenerAdapter() {
                                        override fun onAnimationEnd(animation: Animator) {
                                            binding.container.googleSignup.visibility = View.VISIBLE
                                            binding.container.instaSignup.visibility = View.VISIBLE
                                            binding.container.googleSignup.animate()
                                                .translationYBy((-1500).toFloat()).duration =
                                                400
                                            binding.container.instaSignup.animate()
                                                .translationYBy((-1500).toFloat()).duration =
                                                400
                                            binding.container.enigma.animate()
                                                .translationYBy((-422).toFloat()).duration =
                                                400
                                            binding.container.crypticText.animate()
                                                .translationYBy((-422).toFloat()).duration =
                                                400
                                            startIntent()
                                            animate().setListener(null)


                                        }
                                    })
                        }, 500)


                    }
                })

        }

    }

    fun startIntent() {
        Handler().postDelayed({
            startActivity(Intent(this, AuthActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 500)
    }
}
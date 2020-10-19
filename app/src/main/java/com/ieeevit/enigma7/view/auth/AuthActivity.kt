package com.ieeevit.enigma7.view.auth

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.databinding.ActivityAuthBinding
import com.ieeevit.enigma7.utils.PrefManager
import com.ieeevit.enigma7.view.timer.CountdownActivity
import com.ieeevit.enigma7.viewModel.SignUpViewModel
import kotlinx.android.synthetic.main.bottom_bar.view.*

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private val redirectUri: String = "http://enigma7.ieeevit.com"
    private lateinit var sharedPreference: PrefManager
    private val viewModel: SignUpViewModel by lazy {
        ViewModelProviders.of(this).get(SignUpViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        if (sharedPreference.isLoggedIn() && sharedPreference.getUserStaus() == true) {
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
            supportFragmentManager.beginTransaction()
                .add(R.id.container, fragment)
                .commit()
        }

        binding.include2.game.setOnClickListener {
            Toast.makeText(applicationContext, "Please Login", Toast.LENGTH_SHORT).show()
        }
        viewModel.authStatus.observe(this, Observer {
            if (it == 1) {
                sharedPreference.setFirstTimeLaunch(false)
                sharedPreference.setIsLoggedIn(true)
                navigateToProfileSetup()
            } else if (it == 0) {
                Toast.makeText(applicationContext, "FAIL", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.authCode.observe(this, Observer {
            if (it != null) {
                sharedPreference.setAuthCode(it.toString())
            }
        })
        viewModel.userStatus.observe(this, Observer {
            if (it != null) {
                sharedPreference.setUserStatus(it)
            }
        })

    }

    override fun onResume() {
        super.onResume()
        val uri: Uri? = intent.data
        if (uri != null && uri.toString().startsWith("http://enigma7.ieeevit.com")) {
            val code: String? = uri.getQueryParameter("code")
            viewModel.getAuthCode(code.toString(), redirectUri)
        }
    }

    private fun navigateToProfileSetup() {
        val fragment = ProfileSetupFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.container, fragment)
            .commit()
    }
}
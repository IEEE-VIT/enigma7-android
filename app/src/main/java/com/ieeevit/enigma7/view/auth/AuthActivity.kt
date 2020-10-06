package com.ieeevit.enigma7.view.auth

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
import com.ieeevit.enigma7.viewModel.SignUpViewModel
import kotlinx.android.synthetic.main.bottom_bar.view.*

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private val redirectUri: String = "http://enigma7.ieeevit.com"
    private lateinit var sharedPreference: PrefManager
    private val viewModel: SignUpViewModel by lazy {
        ViewModelProviders.of(this).get(SignUpViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)
        if (savedInstanceState == null) {
            val fragment = SignUpFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.container, fragment)
                .commit()

        }
        sharedPreference = PrefManager(this)
        binding.include2.game.setOnClickListener {
            Toast.makeText(applicationContext, "Please Login", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onResume() {
        super.onResume()
        val uri: Uri? = intent.data
        if (uri != null && uri.toString().startsWith("http://enigma7.ieeevit.com")) {
            val code: String? = uri.getQueryParameter("code")
            viewModel.getAuthCode(code.toString(), redirectUri)
            viewModel.authStatus.observe(this, Observer {
                if (it == 1) {
                    navigateToProfileSetup()
                } else if (it == 0) {
                    Toast.makeText(applicationContext,"FAIL",Toast.LENGTH_SHORT).show()
                }
            })
            viewModel.authCode.observe(this, Observer {
                if (it != null) {
                    sharedPreference.setAuthCode(it.toString())
                }
            })
            viewModel.userStatus.observe(this, Observer {
                if (it!=null){
                    sharedPreference.setUserStatus(it)
                }
            })
        }
    }

    private fun navigateToProfileSetup() {
        val fragment = ProfileSetupFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.container, fragment)
            .commit()
    }
}
package com.ieeevit.enigma7.view.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.databinding.ActivityAuthBinding
import kotlinx.android.synthetic.main.bottom_bar.view.*

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }

}
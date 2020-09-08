package com.ieeevit.enigma7.view.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.databinding.FragmentProfileSetupBinding
import com.ieeevit.enigma7.view.timer.CountdownActivity

class ProfileSetupFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileSetupBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile_setup, container, false)
        binding.nextButton.setOnClickListener {
            startActivity(Intent(activity,CountdownActivity::class.java))
        }
        return binding.root
    }


}
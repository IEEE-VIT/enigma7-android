package com.ieeevit.enigma7.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.databinding.ActivityAuthBinding
import com.ieeevit.enigma7.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding :FragmentSignUpBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.googleSignup.setOnClickListener { v:View->
            val fragment = ProfileSetupFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()

        }
        return binding.root
    }


}
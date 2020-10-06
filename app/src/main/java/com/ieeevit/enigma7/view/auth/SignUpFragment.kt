package com.ieeevit.enigma7.view.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.databinding.FragmentSignUpBinding
import com.ieeevit.enigma7.viewModel.SignUpViewModel


class SignUpFragment : Fragment() {
    private val redirectUri: String = "http://enigma7.ieeevit.com" // TODO: 02-10-2020 add redirectUri here http://enigma7.ieeevit.com or com.ieeevit.enigma7://callback or enigma7.ieeevit.com:/callback
    private val clientId: String = "55484635453-c46tes445anbidhb2qnmb2qs618mvpni.apps.googleusercontent.com"
    private val viewModel: SignUpViewModel by lazy {
        ViewModelProviders.of(this).get(SignUpViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSignUpBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.googleSignup.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://accounts.google.com/o/oauth2/v2/auth?client_id=" + clientId + "&scope=profile+email&access_type=offline&redirect_uri=" + redirectUri + "&response_type=code")
            )
            startActivity(intent)

        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()


    }
}
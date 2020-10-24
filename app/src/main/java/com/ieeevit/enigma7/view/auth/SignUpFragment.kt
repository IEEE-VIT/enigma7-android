package com.ieeevit.enigma7.view.auth

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.databinding.FragmentSignUpBinding
import com.ieeevit.enigma7.utils.PrefManager
import com.ieeevit.enigma7.viewModel.SignUpViewModel


class SignUpFragment : Fragment() {
    private val redirectUri: String =
        "http://127.0.0.1:8000/"
    private val viewModel: SignUpViewModel by lazy {
        ViewModelProviders.of(this).get(SignUpViewModel::class.java)
    }
    private lateinit var sharedPreference: PrefManager
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreference = PrefManager(this.requireActivity())
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), viewModel.gso)
        viewModel.authStatus.observe(this, {
            if (it == 1) {
                sharedPreference.setFirstTimeLaunch(false)
                sharedPreference.setIsLoggedIn(true)
                navigateToProfileSetup()
            } else if (it == 0) {
                Toast.makeText(activity, "FAIL", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.authCode.observe(this, {
            if (it != null) {
                sharedPreference.setAuthCode(it.toString())
            }
        })
        viewModel.userStatus.observe(this, {
            if (it != null) {
                sharedPreference.setUserStatus(it)
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSignUpBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.googleSignup.setOnClickListener {
            signIn()
        }
        return binding.root
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount?>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val authToken = account?.serverAuthCode
            viewModel.getAuthCode(authToken.toString(), redirectUri)
        } catch (e: ApiException) {
            Log.w(TAG, "handleSignInResult:error", e)
            Toast.makeText(activity, "handleSignInResult:error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun navigateToProfileSetup() {
        val fragment = ProfileSetupFragment()
        parentFragmentManager.beginTransaction()
            .add(R.id.container, fragment)
            .commit()
    }
}
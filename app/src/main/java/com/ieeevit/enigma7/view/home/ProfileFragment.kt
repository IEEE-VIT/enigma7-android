package com.ieeevit.enigma7.view.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.databinding.FragmentProfileBinding
import com.ieeevit.enigma7.utils.PrefManager
import com.ieeevit.enigma7.view.auth.AuthActivity
import com.ieeevit.enigma7.view.auth.ProfileSetupFragment
import com.ieeevit.enigma7.view.main.PlayFragment
import com.ieeevit.enigma7.viewModel.ProfileViewModel


class ProfileFragment : Fragment() {
    private lateinit var sharedPreference: PrefManager
    private val viewModel: ProfileViewModel by lazy {
        ViewModelProviders.of(this).get(ProfileViewModel::class.java)
    }
    private val successString: String = "Successfully logged out."
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        viewModel.userDetails.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.username.text = it.username
                binding.emailId.text = it.email
                binding.solved.text = it.questionAnswered.toString()
                binding.rank.text = it.rank.toString()
                binding.score.text = it.points.toString()
            }
        })
        binding.signOutButton.setOnClickListener {
            val authCode: String? = sharedPreference.getAuthCode()
            mGoogleSignInClient.signOut()
            viewModel.logOut("Token $authCode")
        }
        viewModel.logoutStatus.observe(viewLifecycleOwner, {
            if (it == successString) {
                sharedPreference.clearSharedPreference()
                navToLogin()
            }
        })
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fragment = PlayFragment()
                parentFragmentManager.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit()
            }
        })
        sharedPreference = PrefManager(this.requireActivity())
        val authCode: String? = sharedPreference.getAuthCode()
        viewModel.getUserDetails("Token $authCode")
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), viewModel.gso)
    }

    private fun navToLogin() {
        startActivity(Intent(activity, AuthActivity::class.java))
    }

}
package com.ieeevit.enigma7.view.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.snackbar.Snackbar
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.databinding.FragmentProfileBinding
import com.ieeevit.enigma7.utils.PrefManager
import com.ieeevit.enigma7.view.auth.AuthActivity
import com.ieeevit.enigma7.viewModel.ProfileViewModel
import kotlinx.android.synthetic.main.enigma_title.view.*

class ProfileFragment : Fragment() {
    private lateinit var sharedPreference: PrefManager
    private val successString: String = "Successfully logged out."
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val viewModel: ProfileViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProvider(this, ProfileViewModel.Factory(activity.application))
            .get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.overlayFrame.visibility = View.VISIBLE
        viewModel.userDetails.observe(viewLifecycleOwner, {

            if (it != null) {
                binding.username.text = it.username
                binding.emailId.text = it.email
                binding.solved.text = it.questionAnswered.toString()
                binding.rank.text = it.rank.toString()
                binding.score.text = it.points.toString()
                binding.overlayFrame.visibility = View.GONE
            }

        })
        viewModel.networkStatus.observe(viewLifecycleOwner, {
            if (it == 0) {
                binding.overlayFrame.visibility = View.GONE
                Snackbar.make(
                    binding.scroll,
                    "Please check your internet connection!",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })
        binding.signOutButton.setOnClickListener {
            val authCode: String? = sharedPreference.getAuthCode()
            viewModel.logOut("Token $authCode")
            binding.overlayFrame.visibility = View.VISIBLE

        }
        viewModel.logoutStatus.observe(viewLifecycleOwner, {
            if (it == successString) {
                sharedPreference.clearSharedPreference()
                binding.overlayFrame.visibility = View.GONE
                mGoogleSignInClient.signOut()
                viewModel.clearCacheOnLogOut()
                WorkManager.getInstance().cancelAllWork()
                navToLogin()
            }
        })
        binding.title.instructions.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
                .replace(R.id.container, InstructionsFragment())
                .commit()
        }
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
        viewModel.refreshUserDetailsFromRepository("Token $authCode")
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), viewModel.gso)
    }

    private fun navToLogin() {
        startActivity(Intent(activity, AuthActivity::class.java))
        (activity as MainActivity).finish()
    }

}
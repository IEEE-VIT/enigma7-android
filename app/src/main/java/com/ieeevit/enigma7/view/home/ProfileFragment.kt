package com.ieeevit.enigma7.view.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.databinding.FragmentProfileBinding
import com.ieeevit.enigma7.utils.PrefManager
import com.ieeevit.enigma7.view.auth.AuthActivity
import com.ieeevit.enigma7.view.main.MainActivity
import com.ieeevit.enigma7.view.main.PlayFragment
import com.ieeevit.enigma7.viewModel.ProfileViewModel
import com.ieeevit.enigma7.work.RefreshXpWorker


class ProfileFragment : Fragment() {
    private lateinit var sharedPreference: PrefManager
    private val viewModel: ProfileViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProvider(this, ProfileViewModel.Factory(activity.application))
            .get(ProfileViewModel::class.java)
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
        binding.overlayFrame.visibility=View.VISIBLE
        viewModel.userDetails.observe(viewLifecycleOwner, {

            if (it != null) {
                binding.username.text = it.username
                binding.emailId.text = it.email
                binding.solved.text = it.questionAnswered.toString()
                binding.rank.text = it.rank.toString()
                binding.score.text = it.points.toString()
                binding.overlayFrame.visibility=View.GONE
            }

        })
        viewModel.networkStatus.observe(viewLifecycleOwner,{
            if (it==0){
                binding.overlayFrame.visibility=View.GONE
                Toast.makeText(activity,"User detail Retrieval Failed",Toast.LENGTH_SHORT).show()
            }
        })
        binding.signOutButton.setOnClickListener {
            val authCode: String? = sharedPreference.getAuthCode()
            mGoogleSignInClient.signOut()
            viewModel.clearCacheOnLogOut()
            viewModel.logOut("Token $authCode")
            binding.overlayFrame.visibility=View.VISIBLE
            //WorkManager.getInstance().cancelUniqueWork(RefreshXpWorker.WORK_NAME)
            WorkManager.getInstance().cancelAllWork()
        }
        viewModel.logoutStatus.observe(viewLifecycleOwner, {
            if (it == successString) {
                sharedPreference.clearSharedPreference()
                binding.overlayFrame.visibility=View.GONE
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
        viewModel.refreshUserDetailsFromRepository("Token $authCode")
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), viewModel.gso)
    }

    private fun navToLogin() {
        startActivity(Intent(activity, AuthActivity::class.java))
        (activity as MainActivity).finish()
    }

}
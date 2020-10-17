package com.ieeevit.enigma7.view.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.databinding.FragmentProfileBinding
import com.ieeevit.enigma7.utils.PrefManager
import com.ieeevit.enigma7.view.auth.AuthActivity
import com.ieeevit.enigma7.viewModel.ProfileViewModel


class ProfileFragment : Fragment() {
    private lateinit var sharedPreference: PrefManager
    private val viewModel: ProfileViewModel by lazy {
        ViewModelProviders.of(this).get(ProfileViewModel::class.java)
    }
    private val successString:String="Successfully logged out."
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        viewModel.userDetails.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.username.text = it.username
                binding.emailId.text=it.email
                binding.solved.text=it.questionAnswered.toString()
                binding.rank.text=it.rank.toString()
                binding.score.text=it.points.toString()

            }
        })
        binding.signOutButton.setOnClickListener {
            val authCode: String? = sharedPreference.getAuthCode()
            viewModel.logOut("Token "+authCode)
        }
        viewModel.logoutStatus.observe(viewLifecycleOwner, Observer {
            if(it == successString){
                sharedPreference.clearSharedPreference()
                navToLogin()
            }
        })
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = PrefManager(this.requireActivity())
        val authCode: String? = sharedPreference.getAuthCode()
        viewModel.getUserDetails("Token " + authCode)

    }
    private fun navToLogin(){
        startActivity(Intent(activity,AuthActivity::class.java))
    }
}
package com.ieeevit.enigma7.view.auth

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.databinding.FragmentProfileSetupBinding
import com.ieeevit.enigma7.utils.PrefManager
import com.ieeevit.enigma7.view.timer.CountdownActivity
import com.ieeevit.enigma7.viewModel.ProfileSetupViewModel
import java.util.regex.Pattern


class ProfileSetupFragment : Fragment() {
    private lateinit var sharedPreference: PrefManager
    var usernameEntered = MutableLiveData<Int>()
    var userName: String = ""
    val invalid = "________________\nInvalidUsername.\n"
    private val failure="Incorrect string type for field 'username'"
    private val duplicateUsername="User with this username already exists"
    lateinit var pattern: Pattern
    private val viewModel: ProfileSetupViewModel by lazy {
        ViewModelProviders.of(this).get(ProfileSetupViewModel::class.java)
    }
    private lateinit var overlayFrame: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = PrefManager(this.requireActivity())
        pattern = Pattern.compile("[0-9a-zA-Z]*")
    }

    init {
        usernameEntered.value = 3
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileSetupBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile_setup, container, false)
        binding.usernameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                binding.usernameEditText.afterTextChangedDelayed {
                    val matcher = pattern.matcher(it)
                    if (it == "") {
                        binding.nextButton.visibility = INVISIBLE
                        binding.invalidUsernameText.visibility = INVISIBLE
                    } else if (!matcher.matches()) {
                        val text="$invalid->Cannot Contain \n\t Special Characters"
                        binding.nextButton.visibility = INVISIBLE
                        binding.invalidUsernameText.visibility = VISIBLE
                        binding.invalidUsernameText.text = text
                    } else {
                        binding.invalidUsernameText.visibility = INVISIBLE
                        binding.invalidUsernameText.text = ""
                        binding.nextButton.visibility = VISIBLE
                    }
                    userName = it

                }
            }
        })

        binding.nextButton.setOnClickListener {
            val authCode: String? = sharedPreference.getAuthCode()
            overlayFrame.visibility= VISIBLE
            viewModel.editUsername("Token $authCode", userName)
        }

        viewModel.usernameBody.observe(viewLifecycleOwner, {
            overlayFrame.visibility= GONE
            when {
                it.username == userName -> {
                    sharedPreference.setUserStatus(true)
                    startActivity(Intent(activity, CountdownActivity::class.java))
                }
                it.username ==failure -> {
                    Toast.makeText(activity, failure, Toast.LENGTH_SHORT).show()
                    binding.nextButton.visibility= INVISIBLE
                }
                it.error==duplicateUsername -> {
                    val text ="$invalid-> Username Exists!"
                    binding.invalidUsernameText.text=text
                    binding.invalidUsernameText.visibility= VISIBLE
                    binding.nextButton.visibility= INVISIBLE
                }
            }
        })
        viewModel.usernameChanged.observe(viewLifecycleOwner,{
            if (it==0){
                Toast.makeText(activity, "retrofit response error", Toast.LENGTH_SHORT).show()
            }
        })
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        overlayFrame = (activity as AuthActivity).findViewById(R.id.overlayFrame)

    }
    private fun TextView.afterTextChangedDelayed(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            var timer: CountDownTimer? = null

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                timer?.cancel()
                timer = object : CountDownTimer(1000, 1300) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        afterTextChanged.invoke(editable.toString())
                    }
                }.start()
            }
        })
    }

}
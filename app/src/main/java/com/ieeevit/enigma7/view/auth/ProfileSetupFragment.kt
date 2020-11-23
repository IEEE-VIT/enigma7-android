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
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.databinding.FragmentProfileSetupBinding
import com.ieeevit.enigma7.model.OutreachRequest
import com.ieeevit.enigma7.utils.PrefManager
import com.ieeevit.enigma7.view.timer.CountdownActivity
import com.ieeevit.enigma7.viewModel.ProfileSetupViewModel
import kotlinx.android.synthetic.main.fragment_profile_setup.*
import java.util.regex.Pattern


class ProfileSetupFragment : Fragment() {
    private lateinit var sharedPreference: PrefManager
    private var usernameEntered = MutableLiveData<Int>()
    var userName: String = ""
    val invalid = "________________\nInvalidUsername.\n"
    private val failure = "Incorrect string type for field 'username'"
    private val duplicateUsername = "User with this username already exists"
    lateinit var pattern: Pattern
    private var platformPos: Int = 1
    private var graduationPos: Int = 1
    private val viewModel: ProfileSetupViewModel by lazy {
        ViewModelProvider(this, ProfileSetupViewModel.Factory())
            .get(ProfileSetupViewModel::class.java)
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
        val spinner: Spinner = binding.publicitySpinner
        val graduation = binding.graduation
        val collegeStudent = binding.Yup
        val student = MutableLiveData<Boolean>()
        student.value = collegeStudent.isChecked
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.platforms,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_drop_down_list)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?, selectedItemView: View, position: Int, id: Long
            ) {
                platformPos = position
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {

            }
        }
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.graduation,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_drop_down_list)
            graduation.adapter = adapter
        }
        graduation.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                graduationPos = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }


        }
        student.observe(viewLifecycleOwner, {
            if (it) {
                binding.graduation.visibility = VISIBLE
                binding.graduateDate.visibility = VISIBLE
            } else if (!it) {
                binding.graduation.visibility = GONE
                binding.graduateDate.visibility = GONE
            }
        })
        collegeStudent.setOnClickListener {
            student.value = collegeStudent.isChecked
        }
        binding.Nope.setOnClickListener {
            student.value = !binding.Nope.isChecked
        }
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
                        val text = "$invalid->Cannot Contain \n\t Special Characters"
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
            val year: Int?

            year = (graduation.getItemAtPosition(graduationPos).toString()).toInt()

            val outreachRequest = OutreachRequest(
                collegeStudent.isChecked,
                publicitySpinner.getItemAtPosition(platformPos).toString(),
                year
            )
            val authCode: String? = sharedPreference.getAuthCode()
            overlayFrame.visibility = VISIBLE
            viewModel.editUsername("Token $authCode", userName)
            viewModel.sendOutreachDetails("Token $authCode",outreachRequest)
        }

        viewModel.usernameBody.observe(viewLifecycleOwner, {
            overlayFrame.visibility = GONE
            when {
                it.username == userName -> {
                    sharedPreference.setUserStatus(true)
                    startActivity(Intent(activity, CountdownActivity::class.java))
                }
                it.username == failure -> {
                    Toast.makeText(activity, failure, Toast.LENGTH_SHORT).show()
                    binding.nextButton.visibility = INVISIBLE
                }
                it.error == duplicateUsername -> {
                    val text = "$invalid-> Username Exists!"
                    binding.invalidUsernameText.text = text
                    binding.invalidUsernameText.visibility = VISIBLE
                    binding.nextButton.visibility = INVISIBLE
                }
            }
        })
        viewModel.usernameChanged.observe(viewLifecycleOwner, {
            if (it == 0) {
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
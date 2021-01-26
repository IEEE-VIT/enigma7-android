package com.ieeevit.enigma7.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.utils.PrefManager
import com.ieeevit.enigma7.viewModel.StoryViewModel
import kotlinx.android.synthetic.main.enigma_title.view.*
import kotlinx.android.synthetic.main.fragment_story.view.*

class StoryFragment : Fragment() {
    val viewModel: StoryViewModel by viewModels()
    private lateinit var sharedPreference: PrefManager
    private lateinit var authCode: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_story, container, false)
        root.overlayFrame.visibility = View.VISIBLE
        sharedPreference = PrefManager(this.requireActivity())
        authCode = sharedPreference.getAuthCode()!!
        val username = sharedPreference.getUsername()
        viewModel.refreshCompleteStoryFromRepository("Token $authCode",username!!)
        viewModel.history.observe(viewLifecycleOwner, {
            if (it != null) {
                root.overlayFrame.visibility=View.GONE
                root.story.text = it.storyHistory
            }
            else{
                root.overlayFrame.visibility=View.GONE
            }
        })

        root.instructions.setOnClickListener {
            parentFragmentManager.beginTransaction().setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
                .replace(R.id.container, InstructionsFragment())
                .commit()
        }
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fragment = PlayFragment()
                parentFragmentManager.beginTransaction().add(R.id.container, fragment).commit()
            }
        })
    }
}
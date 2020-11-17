package com.ieeevit.enigma7.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.ieeevit.enigma7.R
import kotlinx.android.synthetic.main.enigma_title.view.*
import kotlinx.android.synthetic.main.fragment_story.view.*


class StoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root=inflater.inflate(R.layout.fragment_story, container, false)

        root.story.setDelay(1)
        root.story.setWithMusic(false)
        root.story.animateText(resources.getString(R.string.story_sample))
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
                parentFragmentManager.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit()
            }
        })
    }
}
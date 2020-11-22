package com.ieeevit.enigma7.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import com.ieeevit.enigma7.R
import kotlinx.android.synthetic.main.fragment_story_snippet.view.*

class StorySnippetFragment(private val story:String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root=inflater.inflate(R.layout.fragment_story_snippet, container, false)

        root.story_snippet.setDelay(1)
        root.story_snippet.setWithMusic(false)
        root.story_snippet.animateText(story)

        root.setOnClickListener {
            transaction(PlayFragment())
        }

        return root
    }

    private fun transaction(fragment: Fragment){
        parentFragmentManager
            .beginTransaction().setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
            .replace(R.id.container, fragment)
            .commit()
    }
}
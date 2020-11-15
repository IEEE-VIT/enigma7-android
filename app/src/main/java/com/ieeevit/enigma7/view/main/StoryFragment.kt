package com.ieeevit.enigma7.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ieeevit.enigma7.R
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

        return root
    }
}
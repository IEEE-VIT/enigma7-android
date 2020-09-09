package com.ieeevit.enigma7.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import com.ieeevit.enigma7.R
import kotlinx.android.synthetic.main.questions_layout.*
import kotlinx.android.synthetic.main.questions_layout.view.*

class PlayFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root:View=inflater.inflate(R.layout.fragment_play, container, false)

        val questionsLayout:RelativeLayout=root.findViewById(R.id.questions_view)
        root.submit_btn.setOnClickListener {  }

        return root
    }
}
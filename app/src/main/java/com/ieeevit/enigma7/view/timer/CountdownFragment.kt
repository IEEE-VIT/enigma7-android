package com.ieeevit.enigma7.view.timer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ieeevit.enigma7.R

class CountdownFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         // TODO: 31-08-2020 navigate to instruction
        return inflater.inflate(R.layout.fragment_countdown, container, false)
    }


}
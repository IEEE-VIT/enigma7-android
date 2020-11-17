package com.ieeevit.enigma7.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.utils.PrefManager


class InstructionsFragment : Fragment() {
    private lateinit var sharedPreference: PrefManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreference.setFirstTimeInstruction(false)
        return inflater.inflate(R.layout.fragment_instructions, container, false)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = PrefManager(this.requireActivity())
    }
}
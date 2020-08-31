package com.ieeevit.enigma7.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentProfileBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false)
        return binding.root
    }


}
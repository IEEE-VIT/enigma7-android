package com.ieeevit.enigma7.view.timer

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.view.main.MainActivity

class CountdownFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        startActivity(Intent(activity, MainActivity::class.java))
        return inflater.inflate(R.layout.fragment_countdown, container, false)
    }


}
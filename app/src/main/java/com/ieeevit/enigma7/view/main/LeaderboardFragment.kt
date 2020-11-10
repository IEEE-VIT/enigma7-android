package com.ieeevit.enigma7.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.adapter.LeaderBoardAdapter
import com.ieeevit.enigma7.model.LeaderboardEntry
import com.ieeevit.enigma7.utils.PrefManager
import com.ieeevit.enigma7.viewModel.LeaderboardViewModel
import kotlinx.android.synthetic.main.fragment_leaderboard.view.*

class LeaderboardFragment : Fragment() {

    private lateinit var sharedPreference: PrefManager
    private lateinit var authCode: String
    private lateinit var adapter:LeaderBoardAdapter
    private val viewModel: LeaderboardViewModel by lazy {

        ViewModelProviders.of(this).get(LeaderboardViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root=inflater.inflate(R.layout.fragment_leaderboard, container, false)

        sharedPreference = PrefManager(this.requireActivity())
        authCode=sharedPreference.getAuthCode()!!

        viewModel.getLeaderBoard("Token $authCode")
        root.leaderboard_progress_bar.visibility=View.VISIBLE

        viewModel.mLeaderBoardData.observe(viewLifecycleOwner, {
            adapter= LeaderBoardAdapter(requireContext(), it)
            root.leaderboard.layoutManager=LinearLayoutManager(context)
            root.leaderboard.adapter=adapter
            root.leaderboard_progress_bar.visibility=View.GONE

            adapter.notifyDataSetChanged()
        })

        return root
    }
}
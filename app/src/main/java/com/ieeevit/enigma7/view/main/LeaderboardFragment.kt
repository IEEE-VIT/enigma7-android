package com.ieeevit.enigma7.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.adapter.LeaderBoardAdapter
import com.ieeevit.enigma7.utils.PrefManager
import com.ieeevit.enigma7.viewModel.LeaderboardViewModel
import kotlinx.android.synthetic.main.fragment_leaderboard.view.*

class LeaderboardFragment : Fragment() {

    private lateinit var sharedPreference: PrefManager
    private lateinit var authCode: String
    private lateinit var adapter:LeaderBoardAdapter
    private val viewModel: LeaderboardViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProvider(this, LeaderboardViewModel.Factory(activity.application))
            .get(LeaderboardViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root=inflater.inflate(R.layout.fragment_leaderboard, container, false)
        root.leaderboard_progress_bar.visibility=View.VISIBLE
        sharedPreference = PrefManager(this.requireActivity())
        authCode=sharedPreference.getAuthCode()!!
        viewModel.refreshLeaderBoardFromRepository("Token $authCode")


        viewModel.mLeaderBoardData.observe(viewLifecycleOwner, {
            if (it!=null){
                adapter= LeaderBoardAdapter(requireContext(), it)
                root.leaderboard.layoutManager=LinearLayoutManager(context)
                root.leaderboard.adapter=adapter
                adapter.notifyDataSetChanged()
                adapter.dataLoadStatus.observe(viewLifecycleOwner,{ti->
                    if (ti==1){
                        root.leaderboard_progress_bar.visibility=View.GONE
                    }
                })
            }

        })

        return root
    }
}
package com.ieeevit.enigma7.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.database.Leaderboard

class LeaderBoardAdapter internal constructor(
    private val context: Context,
    private val entries: List<Leaderboard>
) :


RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder>(){
    val dataLoadStatus = MutableLiveData<Int>()
    class ViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var rank: TextView = itemView.findViewById(R.id.rank)
        var username: TextView = itemView.findViewById(R.id.username)
        var solved: TextView = itemView.findViewById(R.id.solved)
        var score: TextView = itemView.findViewById(R.id.score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.leaderboard_entry, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rank=(position + 1).toString() + "."
        holder.rank.text = rank
        holder.username.text = entries[position].username
        holder.score.text = entries[position].points.toString()
        holder.solved.text = entries[position].questionAnswered.toString()
        dataLoadStatus.value=1

    }

    override fun getItemCount(): Int {
        return entries.size
    }
}
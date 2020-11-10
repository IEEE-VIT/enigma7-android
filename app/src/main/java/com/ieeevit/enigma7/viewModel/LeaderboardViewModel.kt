package com.ieeevit.enigma7.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ieeevit.enigma7.api.service.Api
import com.ieeevit.enigma7.model.LeaderboardEntry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LeaderboardViewModel:ViewModel(){
    private val leaderboardData=MutableLiveData<ArrayList<LeaderboardEntry>>()
    val mLeaderBoardData: LiveData<ArrayList<LeaderboardEntry>>
        get() = leaderboardData
    val context=this

    fun getLeaderBoard(authToken:String){
       Api.retrofitService.getLeaderboard(authToken).enqueue(object : Callback<ArrayList<LeaderboardEntry>> {
           override fun onResponse(
               call: Call<ArrayList<LeaderboardEntry>>,
               response: Response<ArrayList<LeaderboardEntry>>
           ) {
               if(!response.body().isNullOrEmpty()) {
                   leaderboardData.value = response.body()
               }
           }

           override fun onFailure(call: Call<ArrayList<LeaderboardEntry>>, t: Throwable) {
               Log.i("ERROR","Leaderboard retrieval Failed",t)
           }

       })
    }
}
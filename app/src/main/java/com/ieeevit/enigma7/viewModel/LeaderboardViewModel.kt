package com.ieeevit.enigma7.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.ieeevit.enigma7.api.service.Api
import com.ieeevit.enigma7.database.getDatabase
import com.ieeevit.enigma7.model.LeaderboardEntry
import com.ieeevit.enigma7.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class LeaderboardViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(getDatabase(application))
    val context = this
    val mLeaderBoardData=repository.leaderBoard
    fun refreshLeaderBoardFromRepository(authToken: String) {
        viewModelScope.launch {
            try {
                repository.refreshLeaderBoard(authToken)
            } catch (e: Exception) {
                Log.i("LeaderBoard Retrieval FAIL", e.toString())
            }
        }
    }


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LeaderboardViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LeaderboardViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}


package com.ieeevit.enigma7.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ieeevit.enigma7.database.getDatabase
import com.ieeevit.enigma7.repository.Repository
import kotlinx.coroutines.launch

class LeaderboardViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(getDatabase(application))
    val context = this
    val mLeaderBoardData = repository.leaderBoard
    fun refreshLeaderBoardFromRepository(authToken: String) {
        viewModelScope.launch {
            try {
                repository.refreshLeaderBoard(authToken)
            } catch (e: Exception) {

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


package com.ieeevit.enigma7.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ieeevit.enigma7.database.getDatabase
import com.ieeevit.enigma7.repository.Repository
import kotlinx.coroutines.launch

class StoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(getDatabase(application))
    val context = this


    val history = repository.storyHistory

    fun refreshCompleteStoryFromRepository(authToken: String) {
        viewModelScope.launch {
            try {
                repository.refreshStoryHistory(authToken)
            } catch (e: Exception) {

            }
        }
    }


}


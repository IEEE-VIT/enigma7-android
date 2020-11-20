package com.ieeevit.enigma7.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.ieeevit.enigma7.api.service.Api
import com.ieeevit.enigma7.database.getDatabase
import com.ieeevit.enigma7.model.Story
import com.ieeevit.enigma7.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryViewModel(application: Application) : AndroidViewModel(application) {

    val context = this
    private val _history = MutableLiveData<String>()
    val history: LiveData<String>
        get() = _history

    fun getCompleteStory(authToken: String){
        Api.retrofitService.getCompleteStory("Token $authToken")
            .enqueue(object : Callback<ArrayList<Story>> {
                override fun onResponse(
                    call: Call<ArrayList<Story>>,
                    response: Response<ArrayList<Story>>
                ) {
                    var completeStory=""
                    for(story in response.body()!!)
                        if(story.question_story!=null)
                            completeStory+="${story.question_story.story_text} \n\n"
                    _history.value=completeStory
                }

                override fun onFailure(call: Call<ArrayList<Story>>, t: Throwable) {
                    Log.d("Story Retrieval Failed", t.message.toString())
                }
            })
    }
}


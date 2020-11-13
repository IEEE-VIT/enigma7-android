package com.ieeevit.enigma7.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.ieeevit.enigma7.api.service.Api
import com.ieeevit.enigma7.database.getDatabase
import com.ieeevit.enigma7.model.CheckAnswerRequest
import com.ieeevit.enigma7.model.CheckAnswerResponse
import com.ieeevit.enigma7.model.HintResponse
import com.ieeevit.enigma7.model.QuestionResponse
import com.ieeevit.enigma7.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class PlayViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(getDatabase(application))
    private val _hint = MutableLiveData<String>()
    val hint: LiveData<String>
        get() = _hint
    private val _answerResponse = MutableLiveData<CheckAnswerResponse>()
    val answerResponse: LiveData<CheckAnswerResponse>
        get() = _answerResponse

    init {
        _hint.value=null
    }


    val questionResponse = repository.questions
    fun getHint(authToken: String) {

        Api.retrofitService.getHint(authToken).enqueue(object : Callback<HintResponse> {
            override fun onResponse(call: Call<HintResponse>, response: Response<HintResponse>) {
                if (response.body() != null) {
                    val hintResponse: HintResponse? = response.body()
                    _hint.value = hintResponse?.hint
                }
            }

            override fun onFailure(call: Call<HintResponse>, t: Throwable) {
                _hint.value = ""
                Log.i("ERROR","Hint retrieval Failed",t)
            }
        })
    }

    fun checkAnswer(authToken: String, answer: String) {
        Api.retrofitService.checkAnswer(authToken, CheckAnswerRequest(answer))
            .enqueue(object : Callback<CheckAnswerResponse> {
                override fun onResponse(call: Call<CheckAnswerResponse>, response: Response<CheckAnswerResponse>) {
                 if(response.body()!=null){
                     _answerResponse.value=response.body()
                 }
                }

                override fun onFailure(call: Call<CheckAnswerResponse>, t: Throwable) {
                    Log.i("ERROR","Check Answer Failed",t)
                }

            })
    }

    fun refreshQuestionsFromRepository(authToken: String){
        viewModelScope.launch {
            try {
                repository.refreshQuestion(authToken)
            }catch (e:Exception){
                Log.i("ERROR","Question retrieval failed $e")
            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PlayViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PlayViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
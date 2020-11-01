package com.ieeevit.enigma7.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ieeevit.enigma7.api.service.Api
import com.ieeevit.enigma7.model.CheckAnswerRequest
import com.ieeevit.enigma7.model.CheckAnswerResponse
import com.ieeevit.enigma7.model.HintResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayViewModel : ViewModel() {
    private val _hint = MutableLiveData<String>()
    val hint: LiveData<String>
        get() = _hint
    private val _answerResponse = MutableLiveData<CheckAnswerResponse>()
    val answerResponse: LiveData<CheckAnswerResponse>
        get() = _answerResponse

    init {
        _hint.value=null
    }
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
}
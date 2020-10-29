package com.ieeevit.enigma7.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ieeevit.enigma7.api.service.Api
import com.ieeevit.enigma7.model.HintResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayViewModel : ViewModel() {
    private val _hint = MutableLiveData<String>()
    val hint: LiveData<String>
        get() = _hint

    fun getHint(authToken: String) {

        Api.retrofitService.getHint(authToken).enqueue(object : Callback<HintResponse> {
            override fun onResponse(call: Call<HintResponse>, response: Response<HintResponse>) {
                if (response.body() != null) {
                    val hintResponse: HintResponse? = response.body()
                    _hint.value = hintResponse?.hint
                }

            }

            override fun onFailure(call: Call<HintResponse>, t: Throwable) {
                _hint.value=""
            }


        })
    }
}
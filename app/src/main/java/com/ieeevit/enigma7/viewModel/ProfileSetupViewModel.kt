package com.ieeevit.enigma7.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ieeevit.enigma7.api.service.Api
import com.ieeevit.enigma7.model.EditUsernameRequest
import com.ieeevit.enigma7.model.EditUsernameResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileSetupViewModel : ViewModel() {
    private val _usernameChanged = MutableLiveData<Int>()
    val usernameChanged: LiveData<Int>
        get() = _usernameChanged

    init {
        _usernameChanged.value = 3
    }

    private val _usernameBody = MutableLiveData<EditUsernameResponse>()
    val usernameBody: LiveData<EditUsernameResponse>
        get() = _usernameBody

    fun editUsername(authToken: String, userName: String) {
        Api.retrofitService.editUsername(authToken, EditUsernameRequest(userName))
            .enqueue(object : Callback<EditUsernameResponse> {
                override fun onResponse(
                    call: Call<EditUsernameResponse>, response: Response<EditUsernameResponse>
                ) {
                    if (response.body() != null) {
                        _usernameBody.value = response.body()
                        _usernameChanged.value = 1
                    }
                }

                override fun onFailure(call: Call<EditUsernameResponse>, t: Throwable) {
                    _usernameChanged.value = 0
                    Log.i("ERROR", "on username edit failure ", t)
                }
            })
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileSetupViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProfileSetupViewModel() as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
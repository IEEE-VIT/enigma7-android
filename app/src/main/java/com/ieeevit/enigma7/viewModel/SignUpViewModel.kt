package com.ieeevit.enigma7.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ieeevit.enigma7.api.service.Api
import com.ieeevit.enigma7.model.AccessToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {
    private val _authStatus = MutableLiveData<Int>()
    val authStatus: LiveData<Int>
        get() = _authStatus
    private val _authCode = MutableLiveData<String>()
    val authCode: LiveData<String>
        get() = _authCode
    private val _userStatus = MutableLiveData<Boolean>()
    val userStatus: LiveData<Boolean>
        get() = _userStatus

    init {
        _authStatus.value = 3   // 0:fail 1:success
        _authCode.value = null
        _userStatus.value = null
    }

    fun getAuthCode(code: String, redirectUri: String) {
        Api.retrofitService.getAccessToken(code, redirectUri)
            .enqueue(object : Callback<AccessToken> {
                override fun onResponse(
                    call: Call<AccessToken>,
                    response: Response<AccessToken>
                ) {
                    if (response.body() != null) {
                        Log.i("AUthKEY", response.body().toString())
                        val result: AccessToken? = response.body()
                        _authCode.value = result?.authorizationKey
                        _userStatus.value = result?.usernameExist
                        _authStatus.value = 1
                    }
                }

                override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                    _authStatus.value = 0
                }
            })
    }
}
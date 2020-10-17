package com.ieeevit.enigma7.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ieeevit.enigma7.api.service.Api
import com.ieeevit.enigma7.model.LogoutResponse
import com.ieeevit.enigma7.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel:ViewModel() {
    private val _userDetails = MutableLiveData<User>()
    val userDetails: LiveData<User>
        get() = _userDetails
    private val _logoutStatus = MutableLiveData<String>()
    val logoutStatus: LiveData<String>
        get() = _logoutStatus
    init {
        _logoutStatus.value=null
    }

    fun getUserDetails(authToken: String){
        Api.retrofitService.getUserDetails(authToken).enqueue(object :Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.body()!=null){
                    _userDetails.value = response.body()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }
    fun logOut(authToken: String){
        Api.retrofitService.logOut(authToken).enqueue((object :Callback<LogoutResponse>{
            override fun onResponse(
                call: Call<LogoutResponse>,
                response: Response<LogoutResponse>
            ) {
                if (response.body()!=null){
                    val body:LogoutResponse?=response.body()
                    _logoutStatus.value=body?.detail
                }
            }

            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        }))
    }
}
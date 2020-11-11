package com.ieeevit.enigma7.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope
import com.ieeevit.enigma7.api.service.Api
import com.ieeevit.enigma7.model.LogoutResponse
import com.ieeevit.enigma7.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    private val _userDetails = MutableLiveData<User>()
    val userDetails: LiveData<User>
        get() = _userDetails
    private val _logoutStatus = MutableLiveData<String>()
    val logoutStatus: LiveData<String>
        get() = _logoutStatus
    private val clientId: String = "55484635453-c46tes445anbidhb2qnmb2qs618mvpni.apps.googleusercontent.com"

    init {
        _logoutStatus.value = null
    }

    val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestScopes(Scope(Scopes.DRIVE_APPFOLDER))
        .requestServerAuthCode(clientId)
        .requestEmail()
        .build()

    fun getUserDetails(authToken: String) {
        Api.retrofitService.getUserDetails(authToken).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.body() != null) {
                    _userDetails.value = response.body()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.i("get user details FAIL",t.toString())
            }
        })
    }

    fun logOut(authToken: String) {
        Api.retrofitService.logOut(authToken).enqueue((object : Callback<LogoutResponse> {
            override fun onResponse(
                call: Call<LogoutResponse>,
                response: Response<LogoutResponse>
            ) {
                if (response.body() != null) {
                    val body: LogoutResponse? = response.body()
                    _logoutStatus.value = body?.detail
                }
            }

            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                Log.i("log out FAIL",t.toString())
            }
        }))
    }
}
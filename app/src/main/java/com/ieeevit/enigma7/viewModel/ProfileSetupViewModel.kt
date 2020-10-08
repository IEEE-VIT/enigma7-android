package com.ieeevit.enigma7.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
        _usernameChanged.value=3
    }

    fun editUsername(authToken: String, userName: String) {
        Api.retrofitService.editUsername(authToken, EditUsernameRequest(userName))
            .enqueue(object : Callback<EditUsernameResponse> {
                override fun onResponse(call: Call<EditUsernameResponse>, response: Response<EditUsernameResponse>
                ) {
                    if (response.body() != null) {
                        val result: EditUsernameResponse? = response.body()
                      //  val somethin: String? = result?.username
                        _usernameChanged.value=1
                        // TODO: 09-10-2020 username validation here
                    }

                }

                override fun onFailure(call: Call<EditUsernameResponse>, t: Throwable) {
                  _usernameChanged.value=0
                }

            })
    }
}
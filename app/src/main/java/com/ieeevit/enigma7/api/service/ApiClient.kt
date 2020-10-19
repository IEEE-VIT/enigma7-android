package com.ieeevit.enigma7.api.service

import com.ieeevit.enigma7.model.AccessToken
import com.ieeevit.enigma7.model.EditUsernameRequest
import com.ieeevit.enigma7.model.EditUsernameResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl("https://enigma7-backend.herokuapp.com/")
    .build()

interface ApiClient {

    @POST("api/v1/users/auth/google/")
    @FormUrlEncoded
    fun getAccessToken(@Field("code") code: String, @Field("callback_url") callBackUrl: String): Call<AccessToken>

    @PATCH("api/v1/users/me/edit/")
    @Headers("Content-Type: application/json")
    fun editUsername(@Header("Authorization") authToken: String, @Body username: EditUsernameRequest): Call<EditUsernameResponse>

}


object Api {
    val retrofitService: ApiClient by lazy {
        retrofit.create(ApiClient::class.java)
    }

}
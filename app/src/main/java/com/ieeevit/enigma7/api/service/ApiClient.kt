package com.ieeevit.enigma7.api.service

import com.ieeevit.enigma7.model.AccessToken
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl("https://enigma7-backend.herokuapp.com/")
    .build()
interface ApiClient {

    @POST("api/v1/users/auth/google/")
    @FormUrlEncoded
    fun getAccessToken(@Field("code") code:String, @Field("callback_url") callBackUrl:String): Call<AccessToken>
}




object ApiAuth {
    val retrofitService:ApiClient by lazy {
        retrofit.create(ApiClient::class.java)
    }

}
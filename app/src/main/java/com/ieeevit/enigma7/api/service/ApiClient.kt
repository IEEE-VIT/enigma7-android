package com.ieeevit.enigma7.api.service

import com.ieeevit.enigma7.model.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl("https://enigma-api-staging.ieeevit.org/")
    .build()

interface ApiClient {

    @POST("api/v1/users/auth/google/")
    @FormUrlEncoded
    fun getAccessToken(
        @Field("code") code: String,
        @Field("callback_url") callBackUrl: String
    ): Call<AccessToken>

    @PATCH("api/v1/users/me/edit/")
    @Headers("Content-Type: application/json")
    fun editUsername(@Header("Authorization") authToken: String, @Body username: EditUsernameRequest): Call<EditUsernameResponse>

    @GET("api/v1/users/me/")
    suspend fun getUserDetails(@Header("Authorization") authToken: String): User

    @POST("api/v1/users/logout/")
    fun logOut(@Header("Authorization") authToken: String): Call<LogoutResponse>

    @GET("api/v1/game/hint/")
    fun getHint(@Header("Authorization") authToken: String): Call<HintResponse>

    @GET("api/v1/game/leaderboard/")
    suspend fun getLeaderboard(@Header("Authorization") authToken: String): ArrayList<LeaderboardEntry>

    @GET("api/v1/game/question/")
    suspend fun getQuestion(@Header("Authorization") authToken: String): QuestionResponse

    @POST("api/v1/game/answer/")
    fun checkAnswer(@Header("Authorization") authToken: String, @Body answerRequest: CheckAnswerRequest): Call<CheckAnswerResponse>

    @POST("api/v1/game/powerup/close-answer/")
    fun usePowerUpCloseAnswer(@Header("Authorization") authToken: String): Call<PowerupResponse>

    @POST("api/v1/game/powerup/skip/")
    fun usePowerUpSkip(@Header("Authorization") authToken: String): Call<PowerupResponse>

    @GET("api/v1/game/powerup/hint/")
    fun usePowerUpHint(@Header("Authorization") authToken: String): Call<PowerupResponse>

    @GET("api/v1/game/status/")
    fun getEnigmaStatus(@Header("Authorization") authToken: String):Call<StatusResponse>
}

object Api {
    val retrofitService: ApiClient by lazy {
        retrofit.create(ApiClient::class.java)
    }

}
package com.ieeevit.enigma7.model

import com.google.gson.annotations.SerializedName

class LeaderboardEntry (
    @SerializedName("username") val username : String,
    @SerializedName("points") val points : Int,
    @SerializedName("question_answered") val questionAnswered : Int
)
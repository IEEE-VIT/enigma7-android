package com.ieeevit.enigma7.model

import com.google.gson.annotations.SerializedName

data class PowerupResponse (

    @SerializedName("detail") val detail : String,
    @SerializedName("status") val status : Boolean,
    @SerializedName("question_id") val question_id : Int,
    @SerializedName("xp") val xp : Int,
    @SerializedName("hint") val hint : String
)
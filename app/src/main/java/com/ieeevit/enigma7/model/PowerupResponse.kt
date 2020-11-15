package com.ieeevit.enigma7.model

import com.google.gson.annotations.SerializedName

data class PowerupResponse (

    @SerializedName("detail") val detail : String,
    @SerializedName("status") val status : Boolean
)
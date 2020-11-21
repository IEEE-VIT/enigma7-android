package com.ieeevit.enigma7.model

import com.google.gson.annotations.SerializedName

data class StatusResponse(

    @SerializedName("has_started")
    val status: Boolean,

    @SerializedName("detail")
    val detail: String,
    @SerializedName("start_date")
    val day: String,

    @SerializedName("start_time")
    val time: String
)
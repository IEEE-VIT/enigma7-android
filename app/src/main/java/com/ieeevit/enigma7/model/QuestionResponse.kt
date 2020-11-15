package com.ieeevit.enigma7.model

import com.google.gson.annotations.SerializedName

data class QuestionResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("img_url") val img_url: String,
    @SerializedName("text") val text: String
)
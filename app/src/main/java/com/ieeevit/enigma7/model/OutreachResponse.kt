package com.ieeevit.enigma7.model


import com.google.gson.annotations.SerializedName

data class OutreachResponse(
    @SerializedName("is_college_student")
    val isCollegeStudent: Boolean,
    @SerializedName("outreach")
    val outreach: String,
    @SerializedName("user")
    val user: Int,
    @SerializedName("year")
    val year: Int
)
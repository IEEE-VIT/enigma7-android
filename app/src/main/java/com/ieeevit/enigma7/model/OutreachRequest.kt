package com.ieeevit.enigma7.model


import com.google.gson.annotations.SerializedName

data class OutreachRequest(
    @SerializedName("is_college_student")
    val isCollegeStudent: Boolean,
    @SerializedName("outreach")
    val outreach: String,
    @SerializedName("year")
    val year: Int?
)
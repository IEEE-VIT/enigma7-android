package com.ieeevit.enigma7.model

import com.google.gson.annotations.SerializedName

data class CheckAnswerRequest(
	@SerializedName("answer")
	val answer: String? = null
)


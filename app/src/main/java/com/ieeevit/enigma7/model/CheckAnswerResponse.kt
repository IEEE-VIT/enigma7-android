package com.ieeevit.enigma7.model

import com.google.gson.annotations.SerializedName

data class CheckAnswerResponse(
	@SerializedName("close_answer")
	val closeAnswer: Boolean? = null,
	@SerializedName("answer")
	val answer: Boolean? = null,
	@SerializedName("detail")
	val detail: String? = null
)


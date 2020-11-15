package com.ieeevit.enigma7.model

import com.google.gson.annotations.SerializedName


data class User(

	@SerializedName("user_status")
	val userStatus: UserStatus?=null,

	@SerializedName("no_of_hints_used")
	val noOfHintsUsed: Int? = null,

	@SerializedName("xp")
	val xp: Int? = null,

	@SerializedName("rank")
	val rank: Int? = null,

	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("question_answered")
	val questionAnswered: Int? = null,

	@SerializedName("email")
	val email: String? = null,

	@SerializedName("username")
	val username: String? = null,

	@SerializedName("points")
	val points: Int? = null
)

data class UserStatus(

	@SerializedName("hint_powerup")
	val hintPowerup: Boolean? = null,

	@SerializedName("skip_powerup")
	val skipPowerup: Boolean? = null,

	@SerializedName("accept_close_answer")
	val acceptCloseAnswer: Boolean? = null,

	@SerializedName("hint_used")
	val hintUsed: Boolean? = null
)

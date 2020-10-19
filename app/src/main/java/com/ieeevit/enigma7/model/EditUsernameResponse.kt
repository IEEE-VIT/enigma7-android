package com.ieeevit.enigma7.model

import com.google.gson.annotations.SerializedName

data class EditUsernameResponse(

	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("username")
	val username: String? = null
)

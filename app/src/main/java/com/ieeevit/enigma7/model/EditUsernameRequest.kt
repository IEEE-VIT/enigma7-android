package com.ieeevit.enigma7.model

import com.google.gson.annotations.SerializedName

data class EditUsernameRequest(

	@SerializedName("username")
	val username: String? = null
)

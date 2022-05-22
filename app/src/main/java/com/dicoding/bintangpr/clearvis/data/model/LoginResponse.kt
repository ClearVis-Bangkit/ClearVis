package com.dicoding.bintangpr.clearvis.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("success")
	val success: Boolean
)

data class Data(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("accessToken")
	val accessToken: String
)

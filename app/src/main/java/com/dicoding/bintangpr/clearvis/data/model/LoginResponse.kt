package com.dicoding.bintangpr.clearvis.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class Data(
	@field:SerializedName("accessToken")
	val accessToken: String
)

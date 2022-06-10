package com.dicoding.bintangpr.clearvis.data.model


import com.google.gson.annotations.SerializedName


data class UploadHistoryResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("success")
	val success: Boolean? = null
)

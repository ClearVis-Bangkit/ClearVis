package com.dicoding.bintangpr.clearvis.data.model

import com.google.gson.annotations.SerializedName

data class ArtikelsResponse(

	@field:SerializedName("data")
	val data: List<DataArticle>,

	@field:SerializedName("success")
	val success: Boolean
)

data class DataArticle(

	@field:SerializedName("img")
	val img: String,

	@field:SerializedName("postBy")
	val postBy: String,

	@field:SerializedName("postDate")
	val postDate: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("url")
	val url: String
)

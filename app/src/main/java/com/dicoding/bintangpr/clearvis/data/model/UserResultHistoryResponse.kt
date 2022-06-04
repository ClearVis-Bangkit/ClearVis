package com.dicoding.bintangpr.clearvis.data.model

import com.google.gson.annotations.SerializedName

data class UserResultHistoryResponse(

    @field:SerializedName("data")
    val data: List<DataItem>,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class DataItem(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)

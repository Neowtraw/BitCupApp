package com.codingub.bitcupapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class FeaturedCollectionDto(
    @SerializedName("id") val id: String,
    @SerializedName("title")  val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("private") val private: Boolean,
    @SerializedName("media_count") val mediaCount: Int,
    @SerializedName("photos_count") val photosCount: Int,
    @SerializedName("videos_count") val videoCount: Int
)
package com.codingub.bitcupapp.data.remote.models

import com.codingub.bitcupapp.data.utils.SrcType
import com.google.gson.annotations.SerializedName

data class CuratedPhotoDto(
    @SerializedName("id") val id: Long,
    @SerializedName("width") val width: Long,
    @SerializedName("height") val height: Long,
    @SerializedName("url") val url: String,
    @SerializedName("photographer") val photographer: String,
    @SerializedName("photographer_url") val photographerUrl: String?,
    @SerializedName("photographer_id") val photographerId: Long,
    @SerializedName("avg_color") val avgColor: String?,
    @SerializedName("src") val src: SrcType,
    @SerializedName("alt") val alt: String
)


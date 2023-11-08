package com.codingub.bitcupapp.data.remote.models

import com.codingub.bitcupapp.data.utils.SrcType

data class CuratedPhotoDto(
    val id: Long,
    val width: Long,
    val height: Long,
    val url: String,
    val photographer: String,
    val photographerUrl: String?,
    val photographerId: Long,
    val avgColor: String?,
    val src: SrcType,
    val liked: Boolean,
    val alt: String

)


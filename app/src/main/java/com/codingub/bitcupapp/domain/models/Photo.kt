package com.codingub.bitcupapp.domain.models

import com.codingub.bitcupapp.data.utils.SrcType

data class Photo(
    val id: Long,
    val width: Long,
    val height: Long,
    val photoUrl: String,
    val photographer: String,
    val photographerUrl: String?,
    val photographerId: Long,
    val avgColor: String?,
    val photoSrc: SrcType,
    val alt: String,
    )
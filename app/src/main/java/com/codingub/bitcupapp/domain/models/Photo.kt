package com.codingub.bitcupapp.domain.models

import com.codingub.bitcupapp.data.utils.SrcType

data class Photo(
    val id: Long,
    val width: Long,
    val height: Long,
    val src: SrcType,
    val photographer: String,
    val favorite: Boolean = false
)
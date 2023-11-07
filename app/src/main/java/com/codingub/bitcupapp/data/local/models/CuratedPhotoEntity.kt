package com.codingub.bitcupapp.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codingub.bitcupapp.data.utils.SrcType

@Entity(tableName = "CuratedPhoto")
data class CuratedPhotoEntity(
    @PrimaryKey
    val id: Long,
    val width: Long,
    val height: Long,
    val photoUrl: String,
    val photographer: String,
    val photographerUrl: String,
    val photographerId: Long,
    val avgColor: String,
    val photoSrc: SrcType,
    val liked: Boolean,
    val alt: String
)




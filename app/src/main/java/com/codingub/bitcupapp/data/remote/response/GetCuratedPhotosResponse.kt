package com.codingub.bitcupapp.data.remote.response

import com.codingub.bitcupapp.data.remote.models.CuratedPhotoDto

data class GetCuratedPhotosResponse(
    val page: Int,
    val perPage: Int,
    val photos: List<CuratedPhotoDto>,
    val nextPage: String
)
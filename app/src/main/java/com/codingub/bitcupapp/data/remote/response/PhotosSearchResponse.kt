package com.codingub.bitcupapp.data.remote.response

import com.codingub.bitcupapp.data.remote.models.CuratedPhotoDto

data class PhotosSearchResponse(
    val totalResults: Long,
    val page: Int,
    val perPage: Int, //default 15 max 80
    val photos: List<CuratedPhotoDto>,
    val nextPage: String

)
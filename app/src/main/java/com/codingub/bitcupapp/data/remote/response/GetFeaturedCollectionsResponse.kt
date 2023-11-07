package com.codingub.bitcupapp.data.remote.response

import com.codingub.bitcupapp.data.remote.models.FeaturedCollectionDto

data class GetFeaturedCollectionsResponse(
    val collections: List<FeaturedCollectionDto>,
    val page: Int,
    val perPage: Int,
    val totalResults: Int,
    val nextPage: String,
    val prevPage: String
)
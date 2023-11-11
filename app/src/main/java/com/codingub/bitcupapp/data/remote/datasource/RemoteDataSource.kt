package com.codingub.bitcupapp.data.remote.datasource

import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.domain.models.Photo

interface RemoteDataSource {

    suspend fun getCuratedPhotos(): List<Photo>
    suspend fun getFeaturedCollections(): List<FeaturedCollection>
    suspend fun searchPhotos(query: String): List<Photo>
    suspend fun getPhoto(id: Long): Photo
}
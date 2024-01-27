package com.codingub.bitcupapp.data.remote.datasource

import com.codingub.bitcupapp.BuildConfig
import com.codingub.bitcupapp.data.mappers.toFeatureCollection
import com.codingub.bitcupapp.data.mappers.toPhoto
import com.codingub.bitcupapp.data.remote.AppApi
import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.domain.models.Photo
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: AppApi
) : RemoteDataSource {

    override suspend fun getCuratedPhotos(): List<Photo> {
        val curatedPhotos = api.getCuratedPhotos(
            BuildConfig.user_token,
            1,
            30
        ).photos
        return curatedPhotos.map { it.toPhoto() }
    }

    override suspend fun getFeaturedCollections(): List<FeaturedCollection> {
        val collections = api.getFeaturedCollections(
            BuildConfig.user_token,
            1,
            7
        ).collections
        return collections.map { it.toFeatureCollection() }
    }

    override suspend fun searchPhotos(query: String): List<Photo> {
        val photos = api.searchPhotos(
            BuildConfig.user_token,
            query
        ).photos
        return photos.map { it.toPhoto() }
    }

    override suspend fun getPhoto(id: Long): Photo {
        val photo = api.getPhoto(
            BuildConfig.user_token,
            id
        )
        return photo.toPhoto()
    }
}
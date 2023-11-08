package com.codingub.bitcupapp.data.remote.datasource

import android.util.Log
import com.codingub.bitcupapp.data.mappers.toFeatureCollection
import com.codingub.bitcupapp.data.mappers.toPhoto
import com.codingub.bitcupapp.data.prefs.AuthConfig
import com.codingub.bitcupapp.data.remote.AppApi
import com.codingub.bitcupapp.data.utils.SrcType
import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.domain.models.Photo
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: AppApi
) : RemoteDataSource{

    override suspend fun getCuratedPhotos(): List<Photo> {
        val curatedPhotos = api.getCuratedPhotos(
            AuthConfig.getKey(),
            1,
            30
        ).photos
        return curatedPhotos.map { it.toPhoto() }
    }

    override suspend fun getFeaturedCollections(): List<FeaturedCollection> {
        val collections = api.getFeaturedCollections(
            AuthConfig.getKey(),
            1,
            7
        ).collections
        return collections.map { it.toFeatureCollection() }
    }

    override suspend fun searchPhotos(query: String): List<Photo> {
        val photos = api.searchPhotos(
            AuthConfig.getKey(),
            query
        ).photos
        return photos.map { it.toPhoto() }
    }

    override suspend fun getPhoto(id: Long): Photo {
        val photo = api.getPhoto(
            AuthConfig.getKey(),
            id
        )
        return photo.toPhoto()
    }
}
package com.codingub.bitcupapp.data.remote

import com.codingub.bitcupapp.data.remote.models.CuratedPhotoDto
import com.codingub.bitcupapp.data.remote.response.GetCuratedPhotosResponse
import com.codingub.bitcupapp.data.remote.response.GetFeaturedCollectionsResponse
import com.codingub.bitcupapp.data.remote.response.PhotosSearchResponse
import com.codingub.bitcupapp.data.utils.Cacheable
import com.codingub.bitcupapp.data.utils.EndPoints.CURATED_PHOTOS
import com.codingub.bitcupapp.data.utils.EndPoints.FEATURED_COLLECTION
import com.codingub.bitcupapp.data.utils.EndPoints.GET_PHOTO
import com.codingub.bitcupapp.data.utils.EndPoints.SEARCH_PHOTO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface AppApi {


    @Cacheable
    @GET(CURATED_PHOTOS)
    suspend fun getCuratedPhotos(
        //pagination (optional)
        @Query("page") page: Long,
        @Query("per_page") perPage: Int
    ): GetCuratedPhotosResponse

    @GET("$GET_PHOTO{id}")
    suspend fun getPhoto(
        @Path("id") id: Long
    ): CuratedPhotoDto

    @GET(SEARCH_PHOTO)
    suspend fun searchPhotos(
        @Query("query") query: String
    ): PhotosSearchResponse

    @Cacheable
    @GET(FEATURED_COLLECTION)
    suspend fun getFeaturedCollections(
        //pagination
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): GetFeaturedCollectionsResponse
}
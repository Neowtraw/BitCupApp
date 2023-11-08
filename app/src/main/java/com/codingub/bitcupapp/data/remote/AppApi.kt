package com.codingub.bitcupapp.data.remote

import com.codingub.bitcupapp.data.remote.response.GetCuratedPhotosResponse
import com.codingub.bitcupapp.data.remote.response.GetFeaturedCollectionsResponse
import com.codingub.bitcupapp.data.remote.response.GetPhotoResponse
import com.codingub.bitcupapp.data.remote.response.PhotosSearchResponse
import com.codingub.bitcupapp.data.utils.EndPoints.CURATED_PHOTOS
import com.codingub.bitcupapp.data.utils.EndPoints.FEATURED_COLLECTION
import com.codingub.bitcupapp.data.utils.EndPoints.GET_PHOTO
import com.codingub.bitcupapp.data.utils.EndPoints.SEARCH_PHOTO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AppApi {


    @GET(CURATED_PHOTOS)
    suspend fun getCuratedPhotos(
       //pagination (optional)
        @Header("Authorization") key: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : GetCuratedPhotosResponse

//    @GET(GET_PHOTO)
//    suspend fun getPhoto(
//        @Header("Authorization") key: String,
//        @Path("id") id: Int
//    ) : GetPhotoResponse

    @GET(SEARCH_PHOTO)
    suspend fun searchPhotos(
        @Header("Authorization") key: String,
        @Query("query") query: String
    ) : PhotosSearchResponse

    @GET(FEATURED_COLLECTION)
    suspend fun getFeaturedCollections(
        //pagination
        @Header("Authorization") key: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : GetFeaturedCollectionsResponse
}
package com.codingub.bitcupapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.bitcupapp.common.ResultState
import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.domain.models.Photo
import com.codingub.bitcupapp.domain.use_cases.GetFeaturedCollections
import com.codingub.bitcupapp.domain.use_cases.GetLastCuratedPhotos
import com.codingub.bitcupapp.domain.use_cases.InitCacheUpdater
import com.codingub.bitcupapp.domain.use_cases.SearchPhotos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFeaturedCollections: GetFeaturedCollections,
    private val getLastCuratedPhotos: GetLastCuratedPhotos,
    private val searchPhotos: SearchPhotos) : ViewModel(){


    private val collectionsLiveData: MutableLiveData<ResultState<List<FeaturedCollection>>> = MutableLiveData(ResultState.Loading())
    fun getCollectionsLiveData(): MutableLiveData<ResultState<List<FeaturedCollection>>> = collectionsLiveData
    private fun setCollectionsLiveData(value: ResultState<List<FeaturedCollection>>) { collectionsLiveData.value = value }

    private val photosLiveData: MutableLiveData<ResultState<List<Photo>>> = MutableLiveData(ResultState.Loading())
    fun getPhotosLiveData(): MutableLiveData<ResultState<List<Photo>>> = photosLiveData
    private fun setPhotosLiveData(value: ResultState<List<Photo>>) { photosLiveData.value = value }

    var lastRequestedAction: (() -> Unit)? = null

    private fun getCollections(){
        viewModelScope.launch {
            val collectionsFlow = getFeaturedCollections()
            collectionsFlow.collect{ collections ->
                setCollectionsLiveData(collections)
            }
        }
    }

    fun getCuratedPhotos(){

        viewModelScope.launch {
            val photosFlow = getLastCuratedPhotos()
            photosFlow.collect{ photos ->
                setPhotosLiveData(photos)
                Log.d("test1","getcurated ${collectionsLiveData.value!!.data}")
            }
        }
    }

    fun searchPhoto(query: String) {
        viewModelScope.launch {
            setPhotosLiveData(ResultState.Loading())
            val photos = searchPhotos(query)
            setPhotosLiveData(photos)
            Log.d("test1","search $query")

        }
    }

    fun updateData(){
        getCollections()
        getCuratedPhotos()
    }

    fun updateLastRequestedAction(action: () -> Unit) {
        lastRequestedAction = action
    }

}
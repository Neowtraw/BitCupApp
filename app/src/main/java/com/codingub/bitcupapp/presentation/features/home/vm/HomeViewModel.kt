package com.codingub.bitcupapp.presentation.features.home.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.bitcupapp.common.ResultState
import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.domain.models.Photo
import com.codingub.bitcupapp.domain.use_cases.GetFeaturedCollections
import com.codingub.bitcupapp.domain.use_cases.GetLastCuratedPhotos
import com.codingub.bitcupapp.domain.use_cases.SearchPhotos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFeaturedCollections: GetFeaturedCollections,
    private val getLastCuratedPhotos: GetLastCuratedPhotos,
    private val searchPhotos: SearchPhotos
) : ViewModel() {


    private val collectionsLiveData: MutableLiveData<ResultState<List<FeaturedCollection>>> =
        MutableLiveData(ResultState.Loading())

    fun getCollectionsLiveData(): MutableLiveData<ResultState<List<FeaturedCollection>>> =
        collectionsLiveData

    private fun setCollectionsLiveData(value: ResultState<List<FeaturedCollection>>) {
        collectionsLiveData.value = value
    }

    private val photosLiveData: MutableLiveData<ResultState<List<Photo>>> =
        MutableLiveData(ResultState.Loading())

    fun getPhotosLiveData(): MutableLiveData<ResultState<List<Photo>>> = photosLiveData
    private fun setPhotosLiveData(value: ResultState<List<Photo>>) {
        photosLiveData.value = value
    }

    val query = MutableStateFlow("")
    private var searchJob : Job? = null


    var lastRequestedAction: (() -> Unit)? = null

    private fun getCollections() {
        viewModelScope.launch {
            val collectionsFlow = getFeaturedCollections()
            collectionsFlow.collect { collections ->
                setCollectionsLiveData(collections)
            }
        }
    }

    fun getCuratedPhotos() {

        viewModelScope.launch {
            val photosFlow = getLastCuratedPhotos()
            photosFlow.collect { photos ->
                setPhotosLiveData(photos)
            }
        }
    }

    @OptIn(FlowPreview::class)
    fun searchPhoto(query: String) {
        setPhotosLiveData(ResultState.Loading())

      //  viewModelScope.launch {
      //      this@HomeViewModel.query.emit(query)


                    searchJob?.cancel()

                    searchJob = viewModelScope.launch {
                        val photos = searchPhotos(query)
                        setPhotosLiveData(photos)
                    }

      //  }
    }

    fun updateData() {
        getCollections()
        getCuratedPhotos()
    }

    fun updateLastRequestedAction(action: () -> Unit) {
        lastRequestedAction = action
    }

}
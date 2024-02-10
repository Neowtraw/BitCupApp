package com.codingub.bitcupapp.presentation.features.home.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.bitcupapp.common.ResultState
import com.codingub.bitcupapp.domain.models.FeaturedCollection
import com.codingub.bitcupapp.domain.models.Photo
import com.codingub.bitcupapp.domain.use_cases.GetFeaturedCollections
import com.codingub.bitcupapp.domain.use_cases.GetLastCuratedPhotos
import com.codingub.bitcupapp.domain.use_cases.SearchPhotos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFeaturedCollectionsUseCase: GetFeaturedCollections,
    private val getLastCuratedPhotosUseCase: GetLastCuratedPhotos,
    private val searchPhotos: SearchPhotos
) : ViewModel() {

    private val _collections: MutableStateFlow<ResultState<List<FeaturedCollection>>> =
        MutableStateFlow(ResultState.Loading())
    val collections: StateFlow<ResultState<List<FeaturedCollection>>> = _collections

    private val _photos: MutableStateFlow<ResultState<List<Photo>>> =
        MutableStateFlow(ResultState.Loading())
    val photos: StateFlow<ResultState<List<Photo>>> = _photos

    private val _lastRequestedAction: MutableStateFlow<(() -> Unit)?> = MutableStateFlow(null)
    val lastRequestedAction: StateFlow<(() -> Unit)?> = _lastRequestedAction

    private var searchJob: Job? = null

    private fun getCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            _photos.value = ResultState.Loading()
            _collections.update { getFeaturedCollectionsUseCase() }
        }
    }

    fun getCuratedPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            _photos.value = ResultState.Loading()
            _photos.update { getLastCuratedPhotosUseCase() }
        }
    }

    fun searchPhoto(query: String) {
        _photos.value = ResultState.Loading()

        searchJob?.cancel()

        searchJob = viewModelScope.launch(Dispatchers.IO) {
            _photos.update { searchPhotos(query) }
        }
    }

    fun updateData() {
        getCollections()
        getCuratedPhotos()
    }

    fun updateLastRequestedAction(action: () -> Unit) {
        _lastRequestedAction.value = action
    }

    override fun onCleared() {
        super.onCleared()
        searchJob = null
    }
}
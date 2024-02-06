package com.codingub.bitcupapp.presentation.features.bookmarks.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.bitcupapp.common.ResultState
import com.codingub.bitcupapp.domain.models.Photo
import com.codingub.bitcupapp.domain.use_cases.GetBookmarkPhotos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val getBookmarkPhotosUseCase: GetBookmarkPhotos
) : ViewModel() {

    private val _bookmarks: MutableStateFlow<List<Photo>> = MutableStateFlow(listOf())
    val bookmarks: StateFlow<List<Photo>> = _bookmarks

    fun getBookmarkPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            getBookmarkPhotosUseCase().collect{
                _bookmarks.value = it
            }

        }
    }
}
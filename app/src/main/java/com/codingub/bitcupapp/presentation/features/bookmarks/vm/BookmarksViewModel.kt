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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val getBookmarkPhotos: GetBookmarkPhotos
) : ViewModel() {

    private val bookmarksLiveData: MutableLiveData<ResultState<List<Photo>>> = MutableLiveData()
    fun getBookmarksLiveData(): LiveData<ResultState<List<Photo>>> = bookmarksLiveData


    fun getBookmarks() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                bookmarksLiveData.value = ResultState.Loading()
            }
            val bookmarks = getBookmarkPhotos()
            withContext(Dispatchers.Main) {
                bookmarks.collect {
                    bookmarksLiveData.value = ResultState.Success(it)
                }
            }
        }
    }
}
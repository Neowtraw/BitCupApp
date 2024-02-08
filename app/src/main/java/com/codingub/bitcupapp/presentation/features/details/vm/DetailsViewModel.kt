package com.codingub.bitcupapp.presentation.features.details.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.bitcupapp.common.ResultState
import com.codingub.bitcupapp.domain.models.Photo
import com.codingub.bitcupapp.domain.use_cases.GetPhoto
import com.codingub.bitcupapp.domain.use_cases.IsBookmarkPhoto
import com.codingub.bitcupapp.domain.use_cases.UpdateBookmarkPhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhoto,
    private val updateBookmarkPhotoUseCase: UpdateBookmarkPhoto,
    private val isBookmarkPhotoUseCase: IsBookmarkPhoto
) : ViewModel() {

    private val _photo: MutableStateFlow<ResultState<Photo>> = MutableStateFlow(ResultState.Loading())
    val photo: StateFlow<ResultState<Photo>> get() = _photo

    private val _isBookmark: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isBookmark: StateFlow<Boolean> get() = _isBookmark

    fun getPhotoInfo(pair: Pair<Long, Boolean>) {
        _photo.value = ResultState.Loading()
        viewModelScope.launch {
            val photo = getPhotoUseCase(pair.first, pair.second)
            _photo.value = photo
        }
    }

    fun isBookmark() {
        viewModelScope.launch(Dispatchers.IO) {
            val existPhoto = isBookmarkPhotoUseCase(_photo.value.data!!.id)
            withContext(Dispatchers.Main) {
                _isBookmark.value = existPhoto
            }
        }
    }

    fun updateBookmark() {

        viewModelScope.launch(Dispatchers.IO) {
            updateBookmarkPhotoUseCase(_photo.value.data!!)
            withContext(Dispatchers.Main) {
                isBookmark()
            }
        }
    }
}
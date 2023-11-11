package com.codingub.bitcupapp.presentation.viewmodels

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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getPhoto: GetPhoto,
    private val updateBookmarkPhoto: UpdateBookmarkPhoto,
    private val isBookmarkPhoto: IsBookmarkPhoto
) : ViewModel() {

    private val _photo: MutableLiveData<ResultState<Photo>> = MutableLiveData()
    val photo: LiveData<ResultState<Photo>> get() = _photo

    private val _isBookmarkLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isBookmarkLiveData: LiveData<Boolean> get() = _isBookmarkLiveData

    fun getPhotoInfo(id: Long) {
        _photo.value = ResultState.Loading()
        viewModelScope.launch {
            val photo = getPhoto(id)
            _photo.value = photo
        }
    }

    fun isBookmark() {
        viewModelScope.launch(Dispatchers.IO) {
            val existPhoto = isBookmarkPhoto(_photo.value!!.data!!.id)
            withContext(Dispatchers.Main) {
                _isBookmarkLiveData.value = existPhoto
            }
        }
    }

    fun updateBookmark() {

        viewModelScope.launch(Dispatchers.IO) {
            updateBookmarkPhoto(_photo.value!!.data!!)
            withContext(Dispatchers.Main) {
                isBookmark()
            }
        }
    }
}
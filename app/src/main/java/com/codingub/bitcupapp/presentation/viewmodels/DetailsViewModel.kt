package com.codingub.bitcupapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.codingub.bitcupapp.common.ResultState
import com.codingub.bitcupapp.domain.models.Photo
import com.codingub.bitcupapp.domain.use_cases.DeleteBookmarkPhoto
import com.codingub.bitcupapp.domain.use_cases.GetPhoto
import com.codingub.bitcupapp.domain.use_cases.InsertBookmarkPhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getPhoto: GetPhoto,
    private val setBookmarkPhoto: InsertBookmarkPhoto,
    private val deleteBookmarkPhoto: DeleteBookmarkPhoto
) : ViewModel() {

    private val _photo: MutableLiveData<ResultState<Photo>> = MutableLiveData()
    val photo: LiveData<ResultState<Photo>> get() = _photo




    fun getPhotoInfo(id: Long) {
        _photo.value = ResultState.Loading()
        viewModelScope.launch {
            val photo = getPhoto(id)
            _photo.value = photo
        }
    }
}
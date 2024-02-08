package com.codingub.bitcupapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * used to assign the data between fragments
 */
class SharedViewModel : ViewModel() {


    private val _photoId : MutableStateFlow<Pair<Long, Boolean>?> = MutableStateFlow(null)
    val photoId : StateFlow<Pair<Long, Boolean>?> get() = _photoId.asStateFlow()

    fun setPhotoId(id: Long, isRemote: Boolean){
        _photoId.value = id to isRemote
    }

}
package com.codingub.bitcupapp.presentation

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * used to assign the data between fragments
 */
class SharedViewModel : ViewModel() {


    private val _photoId = MutableLiveData<Long>()
    val photoId : LiveData<Long> get() = _photoId

    fun setPhotoId(id: Long){
        _photoId.value = id
    }

}
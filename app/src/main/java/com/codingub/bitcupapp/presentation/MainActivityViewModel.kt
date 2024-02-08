package com.codingub.bitcupapp.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
): ViewModel() {

   private val _isLoading = MutableStateFlow(true)
   val isLoading = _isLoading.asStateFlow()

    fun changeLoading(state: Boolean) {
        _isLoading.value = state
    }
}
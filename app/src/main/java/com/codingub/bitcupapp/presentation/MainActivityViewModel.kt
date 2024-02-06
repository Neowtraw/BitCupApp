package com.codingub.bitcupapp.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
): ViewModel() {

    val isLoading = MutableStateFlow(true)
}
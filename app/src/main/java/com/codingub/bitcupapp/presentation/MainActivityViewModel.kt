package com.codingub.bitcupapp.presentation

import androidx.lifecycle.ViewModel
import com.codingub.bitcupapp.domain.use_cases.InitCacheUpdater
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    initCacheUpdater: InitCacheUpdater
): ViewModel() {

    init {
        val res = initCacheUpdater()
    }

    val isLoading = MutableStateFlow(true)
}
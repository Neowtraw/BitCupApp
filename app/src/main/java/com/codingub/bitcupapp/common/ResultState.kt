package com.codingub.bitcupapp.common

sealed class ResultState<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Loading<T>(data: T? = null) : ResultState<T>(data)
    class Success<T>(val value: T) : ResultState<T>(value)
    class Error<T>(throwable: Throwable, data: T? = null) : ResultState<T>(data, throwable)

}
package com.codingub.bitcupapp.data.utils

import com.codingub.bitcupapp.common.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

inline fun <ResultType, RequestType> NetworkBoundResultState(
    crossinline query: () -> Flow<ResultType>,
    crossinline shouldFetch: (ResultType?) -> Boolean = { true },
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { },
    dispatcher: CoroutineDispatcher,
) = flow {

    val data = query().firstOrNull()
    emit(ResultState.Loading(data))

    val flow = when {
        shouldFetch(data) -> {
            emit(ResultState.Loading(data))

            try {
                saveFetchResult(fetch())
                query().map { ResultState.Success(it) }
            } catch (throwable: Throwable) {
                onFetchFailed(throwable)
                query().map { ResultState.Error(throwable, it) }
            }
        }
        else -> {
            query().map { ResultState.Success(it) }
        }
    }
    emitAll(flow)
}.flowOn(dispatcher)
package com.codingub.bitcupapp.data.utils

import com.codingub.bitcupapp.BuildConfig
import com.codingub.bitcupapp.common.ConnectionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class OfflineInterceptor @Inject constructor(
    private val connectionManager: ConnectionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        if (!connectionManager.isConnected) {
            val maxStale = 60 * 60
            builder
                .addHeader("Authorization", BuildConfig.user_token)
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .removeHeader("Pragma")
        }
        return chain.proceed(builder.build())
    }
}
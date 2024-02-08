package com.codingub.bitcupapp.data.utils

import com.codingub.bitcupapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class OnlineInterceptor @Inject constructor() : Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        val maxAge = 60L

        val request = chain.request()
        val builder = request.newBuilder()
            .addHeader("Authorization",  BuildConfig.user_token)
            .header("Cache-Control", "public, max-age=$maxAge")
            .removeHeader("Pragma")

        return chain.proceed(builder.build())
    }
}
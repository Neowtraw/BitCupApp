package com.codingub.bitcupapp.data.utils

import com.codingub.bitcupapp.common.Constants
import com.codingub.bitcupapp.common.Constants.Injection.ENDPOINT
import com.codingub.bitcupapp.common.Constants.Injection.IS_DEBUG
import com.codingub.bitcupapp.data.remote.AppApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

class AppNetworking @Inject constructor(
    @Named(IS_DEBUG) val isDebugMode: Boolean,
    @Named(ENDPOINT) private val endpoint: String,
    private val interceptor: AppInterceptor
){

    private var retrofit: Retrofit? = null
    private var okHttpClient: OkHttpClient? = null

    private fun retrofit(): Retrofit {
        if(retrofit == null) retrofit = retrofitBuilder().build()
        return requireNotNull(retrofit)
    }

    @Synchronized
    open fun okHttpClient(): OkHttpClient {
        if (okHttpClient == null) {
            val builder: OkHttpClient.Builder = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    setLevel(
                        if (isDebugMode) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE
                    )
                })
                .connectTimeout(Constants.DURATION, TimeUnit.SECONDS)
                .readTimeout(Constants.DURATION, TimeUnit.SECONDS)
                .writeTimeout(Constants.DURATION, TimeUnit.SECONDS)

            okHttpClient = builder.build()
        }
        return requireNotNull(okHttpClient)
    }

    private fun retrofitBuilder(): Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient())
    }

    fun historyAppApi(): AppApi = retrofit().create(AppApi::class.java)



}
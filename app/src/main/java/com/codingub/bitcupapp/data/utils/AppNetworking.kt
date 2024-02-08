package com.codingub.bitcupapp.data.utils

import android.content.Context
import com.codingub.bitcupapp.common.Constants
import com.codingub.bitcupapp.common.Constants.Injection.ENDPOINT
import com.codingub.bitcupapp.common.Constants.Injection.IS_DEBUG
import com.codingub.bitcupapp.data.remote.AppApi
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

open class AppNetworking @Inject constructor(
    @Named(IS_DEBUG) val isDebugMode: Boolean,
    @Named(ENDPOINT) private val endpoint: String,
    @ApplicationContext private val context: Context,
    private val cacheInterceptor: CacheInterceptor,
    private val onlineInterceptor: OnlineInterceptor,
    private val offlineInterceptor: OfflineInterceptor
) {

    // cache
    private val cacheSize = (10 * 1024 * 1024L) // 10 MB
    private val cache = Cache(context.cacheDir, cacheSize)

    private var retrofit: Retrofit? = null
    private var okHttpClient: OkHttpClient? = null

    private fun retrofit(): Retrofit {
        if (retrofit == null) retrofit = retrofitBuilder().build()
        return requireNotNull(retrofit)
    }

    @Synchronized
    open fun okHttpClient(): OkHttpClient {
        if (okHttpClient == null) {
            val builder: OkHttpClient.Builder = OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(cacheInterceptor)
                //.addInterceptor(offlineInterceptor)
               // .addNetworkInterceptor(onlineInterceptor)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    setLevel(
                        if (isDebugMode) HttpLoggingInterceptor.Level.BASIC
                        else HttpLoggingInterceptor.Level.NONE
                    )
                })

            okHttpClient = builder.build()
        }
        return requireNotNull(okHttpClient)
    }

    private fun retrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient())
    }

    fun historyAppApi(): AppApi = retrofit().create(AppApi::class.java)


}
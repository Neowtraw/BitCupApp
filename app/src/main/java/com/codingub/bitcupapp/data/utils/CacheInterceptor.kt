package com.codingub.bitcupapp.data.utils

import com.codingub.bitcupapp.BuildConfig
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Cacheable

class CacheInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
            .addHeader("Authorization",  BuildConfig.user_token)

        request.tag(Invocation::class.java)?.let {
            if (!it.method().isAnnotationPresent(Cacheable::class.java)) {
                builder.cacheControl(
                    CacheControl.Builder()
                    .noStore()
                    .build())
                return chain.proceed(builder.build())
            }
            try {
                builder.cacheControl(CacheControl.FORCE_NETWORK)
                return chain.proceed(builder.build())
            } catch (e: Throwable) {
                e.printStackTrace()
            }
            builder.cacheControl(
                CacheControl.Builder()
                .maxAge(1, TimeUnit.HOURS)
                .build())
        }
        return chain.proceed(builder.build())
    }
}
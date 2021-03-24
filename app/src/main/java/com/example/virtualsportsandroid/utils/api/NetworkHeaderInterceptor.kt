package com.example.virtualsportsandroid.utils.api

import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkHeaderInterceptor @Inject constructor(private val sharedPref: SharedPref) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("X-Platform", "android")
            .addHeader("Authorization", sharedPref.token)
            .build()
        return chain.proceed(request)
    }
}

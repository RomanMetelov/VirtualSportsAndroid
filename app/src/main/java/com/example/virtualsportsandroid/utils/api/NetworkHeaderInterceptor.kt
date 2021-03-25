package com.example.virtualsportsandroid.utils.api

import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkHeaderInterceptor @Inject constructor(private val sharedPref: SharedPref) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("X-Platform", "Android")
        if (sharedPref.token.isNotEmpty())
            requestBuilder.addHeader("Authorization", sharedPref.token)
        return chain.proceed(requestBuilder.build())
    }
}

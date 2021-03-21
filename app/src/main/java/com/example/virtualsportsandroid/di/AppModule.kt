package com.example.virtualsportsandroid.di

import android.content.Context
import androidx.annotation.NonNull
import com.example.virtualsportsandroid.login.data.api.LoginService
import com.example.virtualsportsandroid.login.data.api.RegistrationService
import com.example.virtualsportsandroid.utils.api.NetworkHeaderInterceptor
import com.example.virtualsportsandroid.utils.apiHost
import com.example.virtualsportsandroid.utils.schema
import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(@NonNull private val context: Context) {

    @Singleton
    @Provides
    @NonNull
    fun context(): Context = context

    @Singleton
    @Provides
    fun provideSharedPrefs(): SharedPref = SharedPref(context())

}

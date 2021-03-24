package com.example.virtualsportsandroid.di

import android.content.Context
import androidx.annotation.NonNull
import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(@NonNull private val context: Context) {

    @Singleton
    @Provides
    @NonNull
    fun provideContext(): Context = context

    @Singleton
    @Provides
    fun provideSharedPrefs(): SharedPref = SharedPref(context)

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()
}

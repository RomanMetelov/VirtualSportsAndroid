package com.example.virtualsportsandroid.di

import android.content.Context
import androidx.annotation.NonNull
import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context = context

    @Singleton
    @Provides
    fun provideSharedPrefs(): SharedPref = SharedPref(context)

}

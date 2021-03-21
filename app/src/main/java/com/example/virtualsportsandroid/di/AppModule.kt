package com.example.virtualsportsandroid.di

import android.content.Context
import androidx.annotation.NonNull
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(@NonNull private val context: Context) {

    @Singleton
    @Provides
    @NonNull
    fun provideContext(): Context = context

}

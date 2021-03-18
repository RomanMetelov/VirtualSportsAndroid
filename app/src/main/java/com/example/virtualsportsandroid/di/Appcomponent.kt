package com.example.virtualsportsandroid.di

import android.content.Context
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun context(): Context

}

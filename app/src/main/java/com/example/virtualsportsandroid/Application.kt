package com.example.virtualsportsandroid

import android.app.Application
import com.example.virtualsportsandroid.di.AppComponent
import com.example.virtualsportsandroid.di.AppModule
import com.example.virtualsportsandroid.di.DaggerAppComponent

class Application : Application() {
    private lateinit var daggerComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        daggerComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    fun getComponent(): AppComponent {
        return daggerComponent
    }
}

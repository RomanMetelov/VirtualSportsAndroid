package com.example.virtualsportsandroid.utils.sharedPref

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPref @Inject constructor(context: Context) {

    private companion object {
        const val KEY_TOKEN = "KEY_TOKEN"
        const val KEY_CONFIGS = "KEY_CONFIGS"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("main_shared_preferences", Context.MODE_PRIVATE)
    }

    var token: String by SharedPrefDelegate(sharedPreferences, KEY_TOKEN, "")
    var configsJSON: String by SharedPrefDelegate(sharedPreferences, KEY_CONFIGS, "")
}

package com.example.virtualsportsandroid.filter.data

import android.content.Context
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.main.data.CategoryResponse
import com.example.virtualsportsandroid.main.data.ConfigsResponse
import com.example.virtualsportsandroid.main.data.ProviderResponse
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import com.example.virtualsportsandroid.utils.Result
import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import com.google.gson.Gson
import kotlinx.coroutines.withContext

class FiltersRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val sharedPref: SharedPref,
    private val gson: Gson,
    private val context: Context
) {

    suspend fun getCategories(): Result<List<CategoryResponse>, String> = withContext(dispatcher) {
        val configsJSON = sharedPref.configsJSON
        if (configsJSON.isEmpty()) {
            return@withContext Result.error(context.getString(R.string.not_found_message))
        }
        val configsResponse = gson.fromJson(configsJSON, ConfigsResponse::class.java)
        Result.success(configsResponse.categories)
    }

    suspend fun getProviders(): Result<List<ProviderResponse>, String> = withContext(dispatcher) {
        val configsJSON = sharedPref.configsJSON
        if (configsJSON.isEmpty()) {
            return@withContext Result.error(context.getString(R.string.not_found_message))
        }
        val configsResponse = gson.fromJson(configsJSON, ConfigsResponse::class.java)
        Result.success(configsResponse.providers)
    }
}

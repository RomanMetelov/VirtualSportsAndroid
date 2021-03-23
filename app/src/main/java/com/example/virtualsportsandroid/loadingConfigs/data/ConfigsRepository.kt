package com.example.virtualsportsandroid.loadingConfigs.data

import android.util.Log
import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class ConfigsRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val sharedPref: SharedPref,
    private val gson: Gson
) {
    suspend fun loadConfigs() = withContext(dispatcher) {
        val fakeConfigs = ConfigsResponse(
            listOf(
                GameResponse("id1", "Game 1", "provider1", listOf("top", "category1")),
                GameResponse("id2", "Game 2", "provider1", listOf("top", "category1")),
                GameResponse("id3", "Game 3", "provider2", listOf("top", "category2")),
                GameResponse("id4", "Game 4", "provider2", listOf("category2")),
                GameResponse("id5", "Game 5", "provider3", listOf("favorites", "category2")),
                GameResponse("id6", "Game 6", "provider3", listOf("favorites", "category3")),
                GameResponse("id7", "Game 7", "provider4", listOf("top", "category3")),
                GameResponse("id8", "Game 8", "provider4", listOf("category4")),
                GameResponse("id9", "Game 9", "provider5", listOf("category4")),
                GameResponse("id10", "Game 10", "provider5", listOf("category4")),
            ),
            listOf(
                CategoryResponse("top"),
                CategoryResponse("favorites"),
                CategoryResponse("category1"),
                CategoryResponse("category2"),
                CategoryResponse("category3"),
                CategoryResponse("category4"),
            ),
            listOf(
                ProviderResponse("provider1"),
                ProviderResponse("provider2"),
                ProviderResponse("provider3"),
                ProviderResponse("provider4"),
                ProviderResponse("provider5"),
            )
        )
        sharedPref.configsJSON = gson.toJson(fakeConfigs)
        Log.d("wtf", sharedPref.configsJSON)
    }
}
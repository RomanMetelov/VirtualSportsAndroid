package com.example.virtualsportsandroid.loadingConfigs.data

import android.util.Log
import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConfigsRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val sharedPref: SharedPref,
    private val gson: Gson
) {
    @Suppress("MagicNumber")
    suspend fun loadConfigs() = withContext(dispatcher) {
        delay(1000)
        val fakeConfigs = ConfigsResponse(
            listOf(
                GameResponse(
                    "id1",
                    "Game 1",
                    "provider1",
                    listOf("top", "category1"),
                    listOf("top"),
                    "url1"
                ),
                GameResponse(
                    "id2",
                    "Game 2",
                    "provider1",
                    listOf("top", "category1"),
                    listOf("top"),
                    "url2"
                ),
                GameResponse(
                    "id3",
                    "Game 3",
                    "provider2",
                    listOf("top", "category2"),
                    listOf("top"),
                    "url3"
                ),
                GameResponse(
                    "id4",
                    "Game 4",
                    "provider2",
                    listOf("category2"),
                    listOf("top"),
                    "url4"
                ),
                GameResponse(
                    "id5",
                    "Game 5",
                    "provider3",
                    listOf("favorites", "category2"),
                    listOf("all"),
                    "url5"
                ),
                GameResponse(
                    "id6",
                    "Game 6",
                    "provider3",
                    listOf("favorites", "category3"),
                    listOf("all"),
                    "url6"
                ),
                GameResponse(
                    "id7",
                    "Game 7",
                    "provider4",
                    listOf("top", "category3"),
                    listOf("all"),
                    "url7"
                ),
                GameResponse(
                    "id8",
                    "Game 8",
                    "provider4",
                    listOf("category4"),
                    listOf("all"),
                    "url8"
                ),
                GameResponse(
                    "id9",
                    "Game 9",
                    "provider5",
                    listOf("category4"),
                    listOf("all"),
                    "url9"
                ),
                GameResponse(
                    "id10",
                    "Game 10",
                    "provider5",
                    listOf("category4"),
                    listOf("all"),
                    "url10"
                ),
            ),
            listOf(
                CategoryResponse("top", "url1", "Top"),
                CategoryResponse("favorites", "url2", "Favorites"),
                CategoryResponse("category1", "url3", "Category 1"),
                CategoryResponse("category2", "url4", "Category 2"),
                CategoryResponse("category3", "url5", "Category 3"),
                CategoryResponse("category4", "url6", "Category 4"),
            ),
            listOf(
                ProviderResponse("provider1", "url1", "Provider 1"),
                ProviderResponse("provider2", "url2", "Provider 2"),
                ProviderResponse("provider3", "url3", "Provider 3"),
                ProviderResponse("provider4", "url4", "Provider 4"),
                ProviderResponse("provider5", "url5", "Provider 5"),
            ),
            listOf(
                TagResponse("top", "Top"),
                TagResponse("all", "All")
            )
        )
        sharedPref.configsJSON = gson.toJson(fakeConfigs)
        Log.d("wtf", sharedPref.configsJSON)
    }
}
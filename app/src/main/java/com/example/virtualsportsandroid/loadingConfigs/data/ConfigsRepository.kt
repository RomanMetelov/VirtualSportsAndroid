package com.example.virtualsportsandroid.loadingConfigs.data

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
    @Suppress("MagicNumber", "LongMethod")
    suspend fun loadConfigs() = withContext(dispatcher) {
        delay(1000)
        val fakeConfigs = ConfigsResponse(
            listOf(
                GameResponse(
                    "id1",
                    "Game 1",
                    "provider1",
                    listOf("category1"),
                    listOf("top"),
                    "url1",
                    "imageURL1"
                ),
                GameResponse(
                    "id2",
                    "Game 2",
                    "provider1",
                    listOf("category1"),
                    listOf("top"),
                    "url2",
                    "imageURL2"
                ),
                GameResponse(
                    "id3",
                    "Game 3",
                    "provider2",
                    listOf("category2"),
                    listOf("top"),
                    "url3",
                    "imageURL3"
                ),
                GameResponse(
                    "id4",
                    "Game 4",
                    "provider2",
                    listOf("category2"),
                    listOf("top", "favorites"),
                    "url4",
                    "imageURL4"
                ),
                GameResponse(
                    "id5",
                    "Game 5",
                    "provider3",
                    listOf("category2"),
                    listOf("all", "favorites"),
                    "url5",
                    "imageURL5"
                ),
                GameResponse(
                    "id6",
                    "Game 6",
                    "provider3",
                    listOf("category3"),
                    listOf("all"),
                    "url6",
                    "imageURL6"
                ),
                GameResponse(
                    "id7",
                    "Game 7",
                    "provider4",
                    listOf("category3"),
                    listOf("all"),
                    "url7",
                    "imageURL7"
                ),
                GameResponse(
                    "id8",
                    "Game 8",
                    "provider4",
                    listOf("category4"),
                    listOf("all"),
                    "url8",
                    "imageURL8"
                ),
                GameResponse(
                    "id9",
                    "Game 9",
                    "provider5",
                    listOf("category4"),
                    listOf("all", "recentlyLaunched"),
                    "url9",
                    "imageURL9"
                ),
                GameResponse(
                    "id10",
                    "Game 10",
                    "provider5",
                    listOf("category4"),
                    listOf("all", "recentlyLaunched"),
                    "url10",
                    "imageURL10"
                ),
            ),
            listOf(
                CategoryResponse("category1", "url1", "Category 1"),
                CategoryResponse("category2", "url2", "Category 2"),
                CategoryResponse("category3", "url3", "Category 3"),
                CategoryResponse("category4", "url4", "Category 4"),
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
                TagResponse("all", "All"),
                TagResponse("favorites", "Favorites"),
                TagResponse("recentlyLaunched", "Recently launched")
            )
        )
        sharedPref.configsJSON = gson.toJson(fakeConfigs)
    }
}

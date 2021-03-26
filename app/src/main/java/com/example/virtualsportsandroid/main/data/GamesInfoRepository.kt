package com.example.virtualsportsandroid.main.data

import android.content.Context
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GamesInfoRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val sharedPref: SharedPref,
    private val gson: Gson,
    private val gamesInfoService: GamesInfoService,
    private val context: Context
) {
    private companion object {
        const val FAVOURITES_CATEGORY_ID = "favourites"
        const val FAVOURITES_TAG_ID = "favourites"
        const val RECENTLY_LAUNCHED_CATEGORY_ID = "recent"
        const val RECENTLY_LAUNCHED_TAG_ID = "recent"
    }

    suspend fun loadGames() = withContext(dispatcher) {
        if (sharedPref.token.isNotEmpty()) {
            val gamesInfo = async { gamesInfoService.loadGames() }
            val favorites = async { gamesInfoService.loadFavorites() }
            val recent = async { gamesInfoService.loadRecent() }
            saveToSharedPref(gamesInfo.await(), favorites.await(), recent.await())
        } else {
            val gamesInfo = async { gamesInfoService.loadGames() }
            saveToSharedPref(gamesInfo.await())
        }
    }

    private fun saveToSharedPref(
        gamesInfo: GamesResponse,
        favourites: List<GameResponse> = emptyList(),
        recent: List<GameResponse> = emptyList()
    ) {
        val allGames = gamesInfo.games + favourites.map {
            GameResponse(
                it.id,
                it.displayName,
                it.providerId,
                it.categoriesIds + FAVOURITES_CATEGORY_ID,
                it.tagsIds + FAVOURITES_TAG_ID,
                it.gameURL
            )
        } + recent.map {
            GameResponse(
                it.id,
                it.displayName,
                it.providerId,
                it.categoriesIds + RECENTLY_LAUNCHED_CATEGORY_ID,
                it.tagsIds + RECENTLY_LAUNCHED_TAG_ID,
                it.gameURL
            )
        }
        val newCategories = mutableListOf<CategoryResponse>().apply {
            if (favourites.isNotEmpty()) {
                add(CategoryResponse(FAVOURITES_CATEGORY_ID, "", context.getString(R.string.favourites_category_name)))
            }
            if (recent.isNotEmpty()) {
                add(CategoryResponse(RECENTLY_LAUNCHED_CATEGORY_ID, "", context.getString(R.string.recently_launched_category_name)))
            }
        }.toList()
        val newTags = mutableListOf<TagResponse>().apply {
            if (favourites.isNotEmpty()) {
                add(TagResponse(FAVOURITES_TAG_ID, context.getString(R.string.favourites_category_name)))
            }
            if (recent.isNotEmpty()) {
                add(TagResponse(RECENTLY_LAUNCHED_TAG_ID, context.getString(R.string.recently_launched_category_name)))
            }
        }.toList()
        sharedPref.gamesInfoJSON = gson.toJson(
            GamesResponse(
                allGames, newCategories + gamesInfo.categories, gamesInfo.providers,
                gamesInfo.tags + newTags
            )
        )
    }
}

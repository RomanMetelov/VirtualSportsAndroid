package com.example.virtualsportsandroid.main.data

import android.content.Context
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.utils.Result
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import com.example.virtualsportsandroid.utils.api.NetworkExceptionHandler
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
    private val context: Context,
    private val networkExceptionHandler: NetworkExceptionHandler
) {
    companion object {
        const val FAVOURITES_CATEGORY_ID = "favourites"
        const val FAVOURITES_TAG_ID = "favourites"
        const val RECENTLY_LAUNCHED_CATEGORY_ID = "recent"
        const val RECENTLY_LAUNCHED_TAG_ID = "recent"
    }

    suspend fun loadGames(): Result<Unit, NetworkErrorType> {
        return try {
            withContext(dispatcher) {
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
            Result.success(Unit)
        } catch (e: Exception) {
            networkExceptionHandler.handleError(e)
        }
    }

    @Suppress("LongMethod")
    private fun saveToSharedPref(
        gamesInfo: GamesResponse,
        favourites: List<GameResponse> = emptyList(),
        recent: List<GameResponse> = emptyList()
    ) {
        val allGames = gamesInfo.games.map { gameResponse ->
            val isFavorite = favourites.any { it.id == gameResponse.id }
            val isRecentlyLaunched = recent.any { it.id == gameResponse.id }
            if (isFavorite && isRecentlyLaunched) {
                GameResponse(
                    gameResponse.id,
                    gameResponse.displayName,
                    gameResponse.providerId,
                    gameResponse.categoriesIds + FAVOURITES_CATEGORY_ID + RECENTLY_LAUNCHED_CATEGORY_ID,
                    gameResponse.tagsIds + FAVOURITES_TAG_ID + RECENTLY_LAUNCHED_TAG_ID,
                    gameResponse.gameURL
                )
            } else if (isFavorite) {
                GameResponse(
                    gameResponse.id,
                    gameResponse.displayName,
                    gameResponse.providerId,
                    gameResponse.categoriesIds + FAVOURITES_CATEGORY_ID,
                    gameResponse.tagsIds + FAVOURITES_TAG_ID,
                    gameResponse.gameURL
                )
            } else if (isRecentlyLaunched) {
                GameResponse(
                    gameResponse.id,
                    gameResponse.displayName,
                    gameResponse.providerId,
                    gameResponse.categoriesIds + RECENTLY_LAUNCHED_CATEGORY_ID,
                    gameResponse.tagsIds + RECENTLY_LAUNCHED_TAG_ID,
                    gameResponse.gameURL
                )
            } else {
                gameResponse
            }
        }
        val newCategories = mutableListOf<CategoryResponse>().apply {
            if (favourites.isNotEmpty()) {
                add(
                    CategoryResponse(
                        FAVOURITES_CATEGORY_ID,
                        "",
                        context.getString(R.string.favourites_category_name)
                    )
                )
            }
            if (recent.isNotEmpty()) {
                add(
                    CategoryResponse(
                        RECENTLY_LAUNCHED_CATEGORY_ID,
                        "",
                        context.getString(R.string.recently_launched_category_name)
                    )
                )
            }
        }.toList()
        val newTags = mutableListOf<TagResponse>().apply {
            if (favourites.isNotEmpty()) {
                add(
                    TagResponse(
                        FAVOURITES_TAG_ID,
                        context.getString(R.string.favourites_category_name)
                    )
                )
            }
            if (recent.isNotEmpty()) {
                add(
                    TagResponse(
                        RECENTLY_LAUNCHED_TAG_ID,
                        context.getString(R.string.recently_launched_category_name)
                    )
                )
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

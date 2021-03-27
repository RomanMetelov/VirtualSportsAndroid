package com.example.virtualsportsandroid.games.data

import com.example.virtualsportsandroid.game.data.ScreenGameModel
import com.example.virtualsportsandroid.main.data.GamesResponse
import com.example.virtualsportsandroid.games.domain.FilterByCategoryUseCase
import com.example.virtualsportsandroid.games.domain.FilterByCategoryAndProvidersUseCase
import com.example.virtualsportsandroid.games.domain.FilterByProvidersUseCase
import com.example.virtualsportsandroid.games.domain.FilterByTagUseCase
import com.example.virtualsportsandroid.games.domain.model.GameModel
import com.example.virtualsportsandroid.games.domain.model.GamesList
import com.example.virtualsportsandroid.main.data.GamesInfoRepository
import com.example.virtualsportsandroid.utils.Result
import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Suppress("LongParameterList")
class GamesRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val filterByCategoryUseCase: FilterByCategoryUseCase,
    private val filterByProvidersUseCase: FilterByProvidersUseCase,
    private val filterByCategoryAndProvidersUseCase: FilterByCategoryAndProvidersUseCase,
    private val filterByTagUseCase: FilterByTagUseCase,
    private val sharedPref: SharedPref,
    private val gson: Gson
) {

    suspend fun getGamesWithFirstTag(): Result<GamesList, GamesLoadingError> =
        withContext(dispatcher) {
            val configsJSON = sharedPref.gamesInfoJSON
            if (configsJSON.isEmpty()) {
                return@withContext Result.error(GamesLoadingError.NOT_FOUND)
            }
            val configs = gson.fromJson(configsJSON, GamesResponse::class.java)
            if (configs.tags.isNotEmpty()) {
                with(configs.tags.first()) {
                    Result.success(GamesList(name, filterByTagUseCase(id, configs)))
                }
            } else {
                Result.error(GamesLoadingError.NOT_FOUND)
            }
        }

    suspend fun getAllGamesWithoutFirstTag(): Result<List<GamesList>, GamesLoadingError> =
        withContext(dispatcher) {
            val configsJSON = sharedPref.gamesInfoJSON
            if (configsJSON.isEmpty()) {
                return@withContext Result.error(GamesLoadingError.NOT_FOUND)
            }
            val configs = gson.fromJson(configsJSON, GamesResponse::class.java)
            var tags = emptyList<GamesList>()
            configs.tags.forEach {
                if (it.id != configs.tags.first().id) {
                    tags = tags + GamesList(it.name, filterByTagUseCase(it.id, configs))
                }
            }
            Result.success(tags)
        }

    suspend fun getGamesFilteredByCategory(category: String): Result<List<GameModel>, GamesLoadingError> =
        withContext(dispatcher) {
            val configsJSON = sharedPref.gamesInfoJSON
            if (configsJSON.isEmpty()) {
                return@withContext Result.error(GamesLoadingError.NOT_FOUND)
            }
            val configs = gson.fromJson(configsJSON, GamesResponse::class.java)
            Result.success(filterByCategoryUseCase(category, configs))
        }

    suspend fun getGamesFilteredByProviders(providers: List<String>): Result<List<GameModel>, GamesLoadingError> =
        withContext(dispatcher) {
            val configsJSON = sharedPref.gamesInfoJSON
            if (configsJSON.isEmpty()) {
                return@withContext Result.error(GamesLoadingError.NOT_FOUND)
            }
            val configs = gson.fromJson(configsJSON, GamesResponse::class.java)
            Result.success(filterByProvidersUseCase(providers, configs))
        }

    suspend fun getGamesFilteredByCategoryAndProviders(
        category: String,
        providers: List<String>
    ): Result<List<GameModel>, GamesLoadingError> =
        withContext(dispatcher) {
            val configsJSON = sharedPref.gamesInfoJSON
            if (configsJSON.isEmpty()) {
                return@withContext Result.error(GamesLoadingError.NOT_FOUND)
            }
            val configs = gson.fromJson(configsJSON, GamesResponse::class.java)
            Result.success(filterByCategoryAndProvidersUseCase(category, providers, configs))
        }

    suspend fun getCategoryName(categoryId: String): Result<String, GamesLoadingError> =
        withContext(dispatcher) {
            val configsJSON = sharedPref.gamesInfoJSON
            if (configsJSON.isEmpty()) {
                return@withContext Result.error(GamesLoadingError.NOT_FOUND)
            }
            val configs = gson.fromJson(configsJSON, GamesResponse::class.java)
            Result.success(configs.categories.first { it.id == categoryId }.name)
        }

    suspend fun getScreenGameModel(gameId: String): Result<ScreenGameModel, GamesLoadingError> {
        return withContext(dispatcher) {
            val configsJSON = sharedPref.gamesInfoJSON
            if (configsJSON.isEmpty()) {
                return@withContext Result.error<ScreenGameModel, GamesLoadingError>(
                    GamesLoadingError.NOT_FOUND
                )
            }
            val configs = gson.fromJson(configsJSON, GamesResponse::class.java)
            val gameResponse = configs.games.first { it.id == gameId }
            Result.success(
                ScreenGameModel(
                    gameResponse.id, gameResponse.displayName, gameResponse.gameURL,

                    gameResponse.tagsIds.contains(GamesInfoRepository.FAVOURITES_TAG_ID)
                )
            )
        }
    }
}

package com.example.virtualsportsandroid.mainScreen.data

import com.example.virtualsportsandroid.loadingConfigs.data.ConfigsResponse
import com.example.virtualsportsandroid.mainScreen.domain.FilterByCategoryUseCase
import com.example.virtualsportsandroid.mainScreen.domain.FilterByCategoryAndProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.FilterByProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.FilterByTagUseCase
import com.example.virtualsportsandroid.mainScreen.domain.model.GameModel
import com.example.virtualsportsandroid.mainScreen.domain.model.TagModel
import com.example.virtualsportsandroid.utils.Result
import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class GamesRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val filterByCategoryUseCase: FilterByCategoryUseCase,
    private val filterByProvidersUseCase: FilterByProvidersUseCase,
    private val filterByCategoryAndProvidersUseCase: FilterByCategoryAndProvidersUseCase,
    private val filterByTagUseCase: FilterByTagUseCase,
    private val sharedPref: SharedPref,
    private val gson: Gson
) {

    suspend fun getGamesWithFirstTag(): Result<TagModel, GamesLoadingError> =
        withContext(dispatcher) {
            val configsJSON = sharedPref.configsJSON
            if (configsJSON.isEmpty()) {
                return@withContext Result.error(GamesLoadingError.NOT_FOUND)
            }
            val configs = gson.fromJson(configsJSON, ConfigsResponse::class.java)
            with(configs.tags.first()) {
                Result.success(TagModel(id, name, filterByTagUseCase(id, configs)))
            }
        }

    suspend fun getAllGamesWithoutFirstTag(): Result<List<TagModel>, GamesLoadingError> =
        withContext(dispatcher) {
            val configsJSON = sharedPref.configsJSON
            if (configsJSON.isEmpty()) {
                return@withContext Result.error(GamesLoadingError.NOT_FOUND)
            }
            val configs = gson.fromJson(configsJSON, ConfigsResponse::class.java)
            var tags = emptyList<TagModel>()
            configs.tags.forEach {
                if (it.id != configs.tags.first().id) {
                    tags = tags + TagModel(it.id, it.name, filterByTagUseCase(it.id, configs))
                }
            }
            Result.success(tags)
        }

    suspend fun getGamesFilteredByCategory(category: String): Result<List<GameModel>, GamesLoadingError> =
        withContext(dispatcher) {
            val configsJSON = sharedPref.configsJSON
            if (configsJSON.isEmpty()) {
                return@withContext Result.error(GamesLoadingError.NOT_FOUND)
            }
            val configs = gson.fromJson(configsJSON, ConfigsResponse::class.java)
            Result.success(filterByCategoryUseCase(category, configs))
        }

    suspend fun getGamesFilteredByProviders(providers: List<String>): Result<List<GameModel>, GamesLoadingError> =
        withContext(dispatcher) {
            val configsJSON = sharedPref.configsJSON
            if (configsJSON.isEmpty()) {
                return@withContext Result.error(GamesLoadingError.NOT_FOUND)
            }
            val configs = gson.fromJson(configsJSON, ConfigsResponse::class.java)
            Result.success(filterByProvidersUseCase(providers, configs))
        }

    suspend fun getGamesFilteredByCategoryAndProviders(
        category: String,
        providers: List<String>
    ): Result<List<GameModel>, GamesLoadingError> =
        withContext(dispatcher) {
            val configsJSON = sharedPref.configsJSON
            if (configsJSON.isEmpty()) {
                return@withContext Result.error(GamesLoadingError.NOT_FOUND)
            }
            val configs = gson.fromJson(configsJSON, ConfigsResponse::class.java)
            Result.success(filterByCategoryAndProvidersUseCase(category, providers, configs))
        }
}

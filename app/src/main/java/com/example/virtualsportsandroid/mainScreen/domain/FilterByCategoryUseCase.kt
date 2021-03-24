package com.example.virtualsportsandroid.mainScreen.domain

import com.example.virtualsportsandroid.loadingConfigs.data.ConfigsResponse
import com.example.virtualsportsandroid.mainScreen.domain.model.GameModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FilterByCategoryUseCase @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        category: String,
        configsResponse: ConfigsResponse
    ): List<GameModel> =
        withContext(dispatcher) {
            configsResponse.games.filter { it.categoriesIds.contains(category) }
                .map { GameModel(it.id, it.displayName, it.imageURL) }
        }
}

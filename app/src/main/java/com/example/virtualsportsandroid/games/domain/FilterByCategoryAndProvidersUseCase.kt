package com.example.virtualsportsandroid.games.domain

import com.example.virtualsportsandroid.loadingConfigs.data.ConfigsResponse
import com.example.virtualsportsandroid.games.domain.model.GameModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FilterByCategoryAndProvidersUseCase @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        category: String,
        providers: List<String>,
        configsResponse: ConfigsResponse
    ): List<GameModel> = withContext(dispatcher) {
        configsResponse.games.filter { it.categoriesIds.contains(category) && providers.contains(it.providerId) }
            .map {
                GameModel(it.id, it.displayName, "")//temp solution
            }
    }
}

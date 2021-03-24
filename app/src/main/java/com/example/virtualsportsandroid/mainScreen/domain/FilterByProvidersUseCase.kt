package com.example.virtualsportsandroid.mainScreen.domain

import com.example.virtualsportsandroid.loadingConfigs.data.ConfigsResponse
import com.example.virtualsportsandroid.mainScreen.domain.model.GameModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FilterByProvidersUseCase(
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(
        providers: List<String>,
        configsResponse: ConfigsResponse
    ): List<GameModel> = withContext(dispatcher) {
        configsResponse.games.filter { providers.contains(it.providerId) }
            .map { GameModel(it.id, it.displayName, "") } //temp solution
    }
}

package com.example.virtualsportsandroid.games.domain

import com.example.virtualsportsandroid.main.data.GamesResponse
import com.example.virtualsportsandroid.games.domain.model.GameModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FilterByProvidersUseCase(
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(
        providers: List<String>,
        gamesResponse: GamesResponse
    ): List<GameModel> = withContext(dispatcher) {
        gamesResponse.games.filter { providers.contains(it.providerId) }
            .map { GameModel(it.id, it.displayName, "") } //temp solution
    }
}

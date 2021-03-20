package com.example.virtualsportsandroid.mainScreen.domain

import com.example.virtualsportsandroid.mainScreen.data.model.GamesResponse
import com.example.virtualsportsandroid.mainScreen.domain.model.GameModel
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
            .map { GameModel(it.id, it.displayName, it.imageURL) }
    }
}

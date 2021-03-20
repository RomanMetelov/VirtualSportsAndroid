package com.example.virtualsportsandroid.mainScreen.domain

import com.example.virtualsportsandroid.mainScreen.data.model.GamesResponse
import com.example.virtualsportsandroid.mainScreen.domain.model.GameModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FilterByCategoryAndProvidersUseCase @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        category: String,
        providers: List<String>,
        gamesResponse: GamesResponse
    ): List<GameModel> = withContext(dispatcher) {
        gamesResponse.games.filter { it.categoryIds.contains(category) && providers.contains(it.providerId) }
            .map {
                GameModel(it.id, it.displayName, it.imageURL)
            }
    }
}
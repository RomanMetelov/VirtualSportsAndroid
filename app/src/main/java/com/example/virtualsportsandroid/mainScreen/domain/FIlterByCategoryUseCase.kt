package com.example.virtualsportsandroid.mainScreen.domain

import com.example.virtualsportsandroid.mainScreen.data.model.GamesResponse
import com.example.virtualsportsandroid.mainScreen.domain.model.GameModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FilterByCategoryUseCase @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(category: String, gamesResponse: GamesResponse): List<GameModel> =
        withContext(dispatcher) {
            gamesResponse.games.filter { it.categoryIds.contains(category) }
                .map { GameModel(it.id, it.displayName, it.imageURL) }
        }
}
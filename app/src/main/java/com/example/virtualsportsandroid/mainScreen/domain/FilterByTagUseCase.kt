package com.example.virtualsportsandroid.mainScreen.domain

import com.example.virtualsportsandroid.mainScreen.data.model.GamesResponse
import com.example.virtualsportsandroid.mainScreen.domain.model.GameModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FilterByTagUseCase @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(tag: String, gamesResponse: GamesResponse): List<GameModel> =
        withContext(dispatcher) {
            gamesResponse.games.filter { it.tags.contains(tag) }
                .map { GameModel(it.id, it.displayName, it.imageURL) }
        }
}
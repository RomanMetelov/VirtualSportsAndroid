package com.example.virtualsportsandroid.games.domain

import com.example.virtualsportsandroid.main.data.GamesResponse
import com.example.virtualsportsandroid.games.domain.model.GameModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FilterByTagUseCase @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(tag: String, gamesResponse: GamesResponse): List<GameModel> =
        withContext(dispatcher) {
            gamesResponse.games.filter { it.tagsIds.contains(tag) }
                .map { GameModel(it.id, it.displayName, it.imageURL) }
        }
}

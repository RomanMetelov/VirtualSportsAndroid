package com.example.virtualsportsandroid.mainScreen.domain

import com.example.virtualsportsandroid.loadingConfigs.data.ConfigsResponse
import com.example.virtualsportsandroid.mainScreen.domain.model.GameModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FilterByTagUseCase @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(tag: String, configsResponse: ConfigsResponse): List<GameModel> =
        withContext(dispatcher) {
            configsResponse.games.filter { it.tagsIds.contains(tag) }
                .map { GameModel(it.id, it.displayName, it.imageURL) }
        }
}

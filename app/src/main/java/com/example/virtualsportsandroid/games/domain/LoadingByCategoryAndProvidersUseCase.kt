package com.example.virtualsportsandroid.games.domain

import com.example.virtualsportsandroid.games.data.GamesRepository
import com.example.virtualsportsandroid.games.ui.GamesFragmentState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LoadingByCategoryAndProvidersUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val gamesRepository: GamesRepository
) {
    suspend operator fun invoke(category: String, providers: List<String>): GamesFragmentState =
        withContext(dispatcher) {
            val result = gamesRepository.getGamesFilteredByCategoryAndProviders(category, providers)
            when {
                result.isError -> {
                    return@withContext GamesFragmentState.Error
                }
                result.successResult.isEmpty() -> {
                    return@withContext GamesFragmentState.Error
                }
                else -> {
                    return@withContext GamesFragmentState.FilteredByProvidersAndCategory(result.successResult)
                }
            }
        }
}

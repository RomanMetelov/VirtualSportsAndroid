package com.example.virtualsportsandroid.games.domain

import android.content.Context
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.games.data.GamesRepository
import com.example.virtualsportsandroid.games.ui.GamesFragmentState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LoadingByProvidersUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val gamesRepository: GamesRepository,
    private val context: Context
) {

    suspend operator fun invoke(providers: List<String>): GamesFragmentState =
        withContext(dispatcher) {
            val result = gamesRepository.getGamesFilteredByProviders(providers)
            when {
                result.isError -> {
                    return@withContext GamesFragmentState.Error(context.getString(R.string.not_found_message))
                }
                result.successResult.isEmpty() -> {
                    return@withContext GamesFragmentState.Error(context.getString(R.string.not_found_message))
                }
                else -> {
                    return@withContext GamesFragmentState.FilteredByProviders(result.successResult)
                }
            }
        }
}

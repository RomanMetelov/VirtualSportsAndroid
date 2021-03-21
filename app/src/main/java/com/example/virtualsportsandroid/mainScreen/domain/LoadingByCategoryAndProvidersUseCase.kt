package com.example.virtualsportsandroid.mainScreen.domain

import android.content.Context
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.mainScreen.data.GamesRepository
import com.example.virtualsportsandroid.mainScreen.ui.model.MainFragmentState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LoadingByCategoryAndProvidersUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val gamesRepository: GamesRepository,
    private val context: Context
) {
    suspend operator fun invoke(category: String, providers: List<String>): MainFragmentState =
        withContext(dispatcher) {
            val result = gamesRepository.getGamesFilteredByCategoryAndProviders(category, providers)
            when {
                result.isError -> {
                    return@withContext MainFragmentState.Error(context.getString(R.string.not_found_message))
                }
                result.successResult.isEmpty() -> {
                    return@withContext MainFragmentState.Error(context.getString(R.string.not_found_message))
                }
                else -> {
                    return@withContext MainFragmentState.FilteredByProvidersAndCategory(result.successResult)
                }
            }
        }
}

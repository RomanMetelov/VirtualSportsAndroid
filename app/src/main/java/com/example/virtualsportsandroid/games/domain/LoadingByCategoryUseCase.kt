package com.example.virtualsportsandroid.games.domain

import android.content.Context
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.games.data.GamesRepository
import com.example.virtualsportsandroid.games.ui.GamesFragmentState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LoadingByCategoryUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val gamesRepository: GamesRepository,
    private val context: Context
) {
    suspend operator fun invoke(category: String): GamesFragmentState = withContext(dispatcher) {
        val result = gamesRepository.getGamesFilteredByCategory(category)
        when {
            result.isError -> {
                GamesFragmentState.Error(context.getString(R.string.not_found_message))
            }
            result.successResult.isEmpty() -> {
                GamesFragmentState.Error(context.getString(R.string.not_found_message))
            }
            else -> {
                val categoryNameResult = gamesRepository.getCategoryName(category)
                if (categoryNameResult.isError) {
                    GamesFragmentState.Error(context.getString(R.string.not_found_message))
                } else {
                    GamesFragmentState.FilteredByCategory(
                        categoryNameResult.successResult,
                        result.successResult
                    )
                }
            }
        }
    }
}

package com.example.virtualsportsandroid.mainScreen.domain

import android.content.Context
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.mainScreen.data.GamesRepository
import com.example.virtualsportsandroid.mainScreen.data.model.GamesLoadingError
import com.example.virtualsportsandroid.mainScreen.ui.model.MainFragmentState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class NotFilteredGamesLoadingUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val gamesRepository: GamesRepository,
    private val context: Context
) {
    suspend operator fun invoke(): MainFragmentState = withContext(dispatcher) {
        val allGamesResult = gamesRepository.getAllGames()
        val topGamesResult = gamesRepository.getTopGames()
        when {
            allGamesResult.isError -> {
                when (allGamesResult.errorResult) {
                    GamesLoadingError.NOT_FOUND -> {
                        return@withContext MainFragmentState.Error(context.getString(R.string.not_found_message))
                    }
                }
            }
            topGamesResult.isError -> {
                when (topGamesResult.errorResult) {
                    GamesLoadingError.NOT_FOUND -> {
                        return@withContext MainFragmentState.Error(context.getString(R.string.not_found_message))
                    }
                }
            }
            else -> {
                return@withContext MainFragmentState.NotFiltered(
                    topGamesResult.successResult,
                    allGamesResult.successResult
                )
            }
        }
    }
}

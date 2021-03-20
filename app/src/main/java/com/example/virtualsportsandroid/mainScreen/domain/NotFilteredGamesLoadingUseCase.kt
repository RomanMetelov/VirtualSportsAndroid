package com.example.virtualsportsandroid.mainScreen.domain

import com.example.virtualsportsandroid.mainScreen.data.GamesRepository
import com.example.virtualsportsandroid.mainScreen.data.model.GamesLoadingError
import com.example.virtualsportsandroid.mainScreen.ui.model.MainFragmentState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class NotFilteredGamesLoadingUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val gamesRepository: GamesRepository
) {
    suspend operator fun invoke(): MainFragmentState = withContext(dispatcher) {
        val allGamesResult = gamesRepository.getAllGames()
        val topGamesResult = gamesRepository.getTopGames()
        when {
            allGamesResult.isError -> {
                when(allGamesResult.errorResult) {
                    GamesLoadingError.NOT_FOUND -> {
                        return@withContext MainFragmentState.Error("Not found 404")
                    }
                }
            }
            topGamesResult.isError -> {
                when(topGamesResult.errorResult) {
                    GamesLoadingError.NOT_FOUND -> {
                        return@withContext MainFragmentState.Error("Not found 404")
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
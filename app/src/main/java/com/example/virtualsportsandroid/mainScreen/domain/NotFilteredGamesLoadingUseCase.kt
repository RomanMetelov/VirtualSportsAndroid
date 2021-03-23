package com.example.virtualsportsandroid.mainScreen.domain

import android.content.Context
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.mainScreen.data.GamesRepository
import com.example.virtualsportsandroid.mainScreen.data.GamesLoadingError
import com.example.virtualsportsandroid.mainScreen.ui.model.MainFragmentState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class NotFilteredGamesLoadingUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val gamesRepository: GamesRepository,
    private val context: Context
) {
    suspend operator fun invoke(): MainFragmentState = withContext(dispatcher) {
        val allGamesWithoutFirstTagResult = gamesRepository.getAllGamesWithoutFirstTag()
        val gamesWithFirstTagResult = gamesRepository.getGamesWithFirstTag()
        when {
            allGamesWithoutFirstTagResult.isError -> {
                when (allGamesWithoutFirstTagResult.errorResult) {
                    GamesLoadingError.NOT_FOUND -> {
                        return@withContext MainFragmentState.Error(context.getString(R.string.not_found_message))
                    }
                }
            }
            gamesWithFirstTagResult.isError -> {
                when (gamesWithFirstTagResult.errorResult) {
                    GamesLoadingError.NOT_FOUND -> {
                        return@withContext MainFragmentState.Error(context.getString(R.string.not_found_message))
                    }
                }
            }
            else -> {
                return@withContext MainFragmentState.NotFiltered(
                    gamesWithFirstTagResult.successResult,
                    allGamesWithoutFirstTagResult.successResult
                )
            }
        }
    }
}

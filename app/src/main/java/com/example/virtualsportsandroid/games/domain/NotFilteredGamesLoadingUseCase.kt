package com.example.virtualsportsandroid.games.domain

import android.content.Context
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.games.data.GamesLoadingError
import com.example.virtualsportsandroid.games.data.GamesRepository
import com.example.virtualsportsandroid.games.domain.model.GamesList
import com.example.virtualsportsandroid.games.ui.GamesFragmentState
import com.example.virtualsportsandroid.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class NotFilteredGamesLoadingUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val gamesRepository: GamesRepository,
    private val context: Context
) {
    suspend operator fun invoke(): GamesFragmentState = withContext(dispatcher) {
        handleResult(
            withContext(dispatcher) { gamesRepository.getAllGamesWithoutFirstTag() },
            withContext(dispatcher) { gamesRepository.getGamesWithFirstTag() }
        )
    }

    private fun handleResult(
        allGamesWithoutFirstTagResult: Result<List<GamesList>, GamesLoadingError>,
        gamesWithFirstTagResult: Result<GamesList, GamesLoadingError>
    ) = when {
        allGamesWithoutFirstTagResult.isError -> {
            when (allGamesWithoutFirstTagResult.errorResult) {
                GamesLoadingError.NOT_FOUND -> {
                    GamesFragmentState.Error(context.getString(R.string.not_found_message))
                }
            }
        }
        gamesWithFirstTagResult.isError -> {
            when (gamesWithFirstTagResult.errorResult) {
                GamesLoadingError.NOT_FOUND -> {
                    GamesFragmentState.Error(context.getString(R.string.not_found_message))
                }
            }
        }
        else -> {
            GamesFragmentState.NotFiltered(
                gamesWithFirstTagResult.successResult,
                allGamesWithoutFirstTagResult.successResult
            )
        }
    }
}

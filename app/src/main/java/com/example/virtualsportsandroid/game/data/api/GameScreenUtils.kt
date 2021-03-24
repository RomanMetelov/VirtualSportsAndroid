package com.example.virtualsportsandroid.game.data.api

import com.example.virtualsportsandroid.utils.Result
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import com.example.virtualsportsandroid.utils.api.NetworkExceptionHandler
import javax.inject.Inject

@Suppress("TooGenericExceptionCaught")
class GameScreenUtils @Inject constructor(
    private val gameScreenService: GameScreenService,
    private val networkExceptionHandler: NetworkExceptionHandler
) {

    suspend fun addGameToFavorite(gameId: String): Result<Boolean, NetworkErrorType> {
        return try {
            gameScreenService.addGameToFavorite(gameId = gameId)
            Result.success(true)
        } catch (e: Exception) {
            networkExceptionHandler.handleError(e)
        }
    }

    suspend fun delGameFromFavorite(gameId: String): Result<Boolean, NetworkErrorType> {
        return try {
            gameScreenService.delGameFromFavorite(gameId = gameId)
            Result.success(true)
        } catch (e: Exception) {
            networkExceptionHandler.handleError(e)
        }
    }

}

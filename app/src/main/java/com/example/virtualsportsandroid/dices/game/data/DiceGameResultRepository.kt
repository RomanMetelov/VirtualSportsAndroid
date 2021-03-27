package com.example.virtualsportsandroid.dices.game.data

import android.util.Log
import com.example.virtualsportsandroid.utils.Result
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import com.example.virtualsportsandroid.utils.api.NetworkExceptionHandler
import javax.inject.Inject

@Suppress("TooGenericExceptionCaught")
class DiceGameResultRepository @Inject constructor(
    private val diceGameResultService: DiceGameResultService,
    private val networkExceptionHandler: NetworkExceptionHandler
) {
    suspend fun getDiceGameResult(
        datetime: String,
        betTypeId: Int
    ): Result<DiceGameResultApiModel, NetworkErrorType> {
        return try {
            Result.success(
                diceGameResultService.getDiceGameResult(
                    DiceGameApiModel(dateTime = datetime, betType = betTypeId)
                )
            )
        } catch (e: Exception) {
            Log.d("TAG", "getDiceGameResult: $e")
            networkExceptionHandler.handleError(e)
        }
    }
}

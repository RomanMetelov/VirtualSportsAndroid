package com.example.virtualsportsandroid.dices.history.data

import com.example.virtualsportsandroid.dices.game.data.DiceGameResultApiModel
import com.example.virtualsportsandroid.dices.game.domain.DiceGameResultModel
import com.example.virtualsportsandroid.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@Suppress("EmptyClassBlock", "MagicNumber")
class DiceGameBetHistoryRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val diceGameBetHistoryService: DiceGameBetHistoryService
) {
    suspend fun getDiceGameBetHistory(): Result<List<DiceGameResultModel>, String> =
        withContext(dispatcher) {
            val diceGameBetHistoryApi: List<DiceGameResultModel> =
                diceGameBetHistoryService.getDiceGameBetHistory()
            return@withContext try {
                Result.success(diceGameBetHistoryApi)
            } catch (e: HttpException) {
                Result.error(e.toString())
            }
        }
}

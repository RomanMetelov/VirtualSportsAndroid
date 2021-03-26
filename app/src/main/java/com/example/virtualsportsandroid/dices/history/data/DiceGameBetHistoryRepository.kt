package com.example.virtualsportsandroid.dices.history.data

import com.example.virtualsportsandroid.dices.BetType
import com.example.virtualsportsandroid.dices.DiceGameResultModel
import com.example.virtualsportsandroid.dices.game.data.DiceGameResultService
import com.example.virtualsportsandroid.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.random.Random

@Suppress("EmptyClassBlock", "MagicNumber")
class DiceGameBetHistoryRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val diceGameBetHistoryService: DiceGameBetHistoryService
) {
    suspend fun getDiceGameBetHistory(): Result<List<DiceGameResultModel>, String> =
        withContext(dispatcher) {
            val diceGameBetHistory: List<DiceGameResultModel> =
                diceGameBetHistoryService.getDiceGameBetHistory()
            return@withContext try {
                Result.success(diceGameBetHistory)
            } catch (e: HttpException) {
                Result.error(e.toString())
            }
        }
}

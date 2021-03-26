package com.example.virtualsportsandroid.dices.history.data

import com.example.virtualsportsandroid.dices.BetType
import com.example.virtualsportsandroid.dices.DiceGameResultModel
import com.example.virtualsportsandroid.dices.game.data.DiceGameResultService
import com.example.virtualsportsandroid.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

@Suppress("EmptyClassBlock", "MagicNumber")
class DiceGameBetHistoryRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val diceGameBetHistoryService: DiceGameBetHistoryService
) {

    //написать тут поход на сервер
    suspend fun getDiceGameBetHistory(): Result<List<DiceGameResultModel>, String> = withContext(dispatcher) {
        val diceGameBetHistory: List<DiceGameResultModel> =
            diceGameBetHistoryService.getDiceGameBetHistory()
        return@withContext try {
            Result.success(diceGameBetHistory)
        } catch (e: Exception) {
            Result.error(e.toString())
        }


//        val last100betsResult: MutableList<DiceGameResultModel> = mutableListOf()
//        var randomDiceBet: BetType
//        var diceRollRandomResult: Int
//        var diceGameResult: DiceGameResultModel
//        var isBetWon: Boolean
//
//        for (id in 1..100) {
//            randomDiceBet = getRandomBet()
//            diceRollRandomResult = getDiceRollRandomResult()
//            isBetWon = isBetWon(randomDiceBet, diceRollRandomResult)
//            diceGameResult = DiceGameResultModel(
//                id.toString(),
//                "datetime",
//                randomDiceBet,
//                diceRollRandomResult,
//                isBetWon)
//            last100betsResult.add(diceGameResult)
//        }
//
//        return@withContext Result.success(last100betsResult)
    }

//    private fun getRandomBet(): BetType {
//        return when (Random.nextInt(8)) {
//            0 -> BetType.NUMBER1
//            1 -> BetType.NUMBER2
//            2 -> BetType.NUMBER3
//            3 -> BetType.NUMBER4
//            4 -> BetType.NUMBER5
//            5 -> BetType.NUMBER6
//            6 -> BetType.EVEN
//            else -> BetType.ODD
//        }
//    }
//
//    private fun getDiceRollRandomResult(): Int {
//        return Random.nextInt(6) + 1
//    }
//
//    private fun isBetWon(betType: BetType, diceRollResult: Int): Boolean {
//        return when (betType) {
//            BetType.NUMBER1 -> diceRollResult == 1
//            BetType.NUMBER2 -> diceRollResult == 2
//            BetType.NUMBER3 -> diceRollResult == 3
//            BetType.NUMBER4 -> diceRollResult == 4
//            BetType.NUMBER5 -> diceRollResult == 5
//            BetType.NUMBER6 -> diceRollResult == 6
//            BetType.EVEN -> diceRollResult % 2 == 0
//            else -> diceRollResult % 2 != 0
//        }
//    }
}

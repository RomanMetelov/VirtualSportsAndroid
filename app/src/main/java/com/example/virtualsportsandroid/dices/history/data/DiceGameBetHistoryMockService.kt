package com.example.virtualsportsandroid.dices.history.data

import com.example.virtualsportsandroid.dices.BetType
import com.example.virtualsportsandroid.dices.DiceGameResultModel
import kotlin.random.Random

@Suppress("MagicNumber")
class DiceGameBetHistoryMockService : DiceGameBetHistoryService {
    override suspend fun getDiceGameBetHistory(): List<DiceGameResultModel> {
        val last100betsResult: MutableList<DiceGameResultModel> = mutableListOf()
        var randomDiceBet: BetType
        var diceRollRandomResult: Int
        var diceGameResult: DiceGameResultModel
        var isBetWon: Boolean

        for (id in 1..100) {
            randomDiceBet = getRandomBet()
            diceRollRandomResult = getDiceRollRandomResult()
            isBetWon = isBetWon(randomDiceBet, diceRollRandomResult)
            diceGameResult = DiceGameResultModel(id.toString(), "datetime", randomDiceBet, diceRollRandomResult, isBetWon)
            last100betsResult.add(diceGameResult)
        }

        return last100betsResult
    }

    private fun getRandomBet(): BetType {
        return when (Random.nextInt(8) + 1) {
            1 -> BetType.NUMBER1
            2 -> BetType.NUMBER2
            3 -> BetType.NUMBER3
            4 -> BetType.NUMBER4
            5 -> BetType.NUMBER5
            6 -> BetType.NUMBER6
            7 -> BetType.EVEN
            else -> BetType.ODD
        }
    }

    private fun getDiceRollRandomResult(): Int {
        return Random.nextInt(6) + 1
    }

    private fun isBetWon(betType: BetType, diceRollResult: Int): Boolean {
        return when (betType) {
            BetType.NUMBER1 -> diceRollResult == 1
            BetType.NUMBER2 -> diceRollResult == 2
            BetType.NUMBER3 -> diceRollResult == 3
            BetType.NUMBER4 -> diceRollResult == 4
            BetType.NUMBER5 -> diceRollResult == 5
            BetType.NUMBER6 -> diceRollResult == 6
            BetType.EVEN -> diceRollResult % 2 == 0
            else -> diceRollResult % 2 != 0
        }
    }
}

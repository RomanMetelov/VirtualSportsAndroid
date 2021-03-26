/*
package com.example.virtualsportsandroid.dices.history.data

import com.example.virtualsportsandroid.dices.game.data.BetType
import com.example.virtualsportsandroid.dices.game.data.DiceGameResultApiModel
import kotlin.random.Random

@Suppress("MagicNumber")
class DiceGameBetHistoryMockService : DiceGameBetHistoryService {
    override suspend fun getDiceGameBetHistory(): List<DiceGameResultApiModel> {
        val last100BetsResultApi: MutableList<DiceGameResultApiModel> = mutableListOf()
        var randomDiceBet: BetType
        var diceRollRandomResult: Int
        var diceGameResultApi: DiceGameResultApiModel
        var isBetWon: Boolean

        for (id in 1..100) {
            randomDiceBet = getRandomBet()
            diceRollRandomResult = getDiceRollRandomResult()
            isBetWon = isBetWon(randomDiceBet, diceRollRandomResult)
            diceGameResultApi = DiceGameResultApiModel(
                id.toString(),
                "datetime",
                randomDiceBet,
                diceRollRandomResult,
                isBetWon)
            last100BetsResultApi.add(diceGameResultApi)
        }

        return last100BetsResultApi
    }

    private fun getRandomBet(): BetType {
        return when (Random.nextInt(8)) {
            0 -> BetType.NUMBER1
            1 -> BetType.NUMBER2
            2 -> BetType.NUMBER3
            3 -> BetType.NUMBER4
            4 -> BetType.NUMBER5
            5 -> BetType.NUMBER6
            6 -> BetType.EVEN
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
*/

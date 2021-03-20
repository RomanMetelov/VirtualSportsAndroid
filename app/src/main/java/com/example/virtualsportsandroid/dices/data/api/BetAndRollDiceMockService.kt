package com.example.virtualsportsandroid.dices.data.api

import com.example.virtualsportsandroid.dices.data.models.BetType
import com.example.virtualsportsandroid.dices.data.models.GameResult
import kotlin.random.Random

@Suppress("MagicNumber")
class BetAndRollDiceMockService : BetAndRollDiceService{
    override suspend fun getDiceGameResult(): GameResult {
        return GameResult(1, BetType.NUMBER6, 6, true)
    }
}

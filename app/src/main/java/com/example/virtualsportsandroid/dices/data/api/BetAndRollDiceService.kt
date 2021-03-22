package com.example.virtualsportsandroid.dices.data.api

import com.example.virtualsportsandroid.dices.data.models.GameResult

interface BetAndRollDiceService {
    suspend fun getDiceGameResult() : GameResult
}

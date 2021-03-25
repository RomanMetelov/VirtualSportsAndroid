package com.example.virtualsportsandroid.dices.game.data

import com.example.virtualsportsandroid.dices.DiceGameResultModel

interface DiceGameResultService {
    suspend fun getDiceGameResult() : DiceGameResultModel
}

package com.example.virtualsportsandroid.dices.history.data

import com.example.virtualsportsandroid.dices.DiceGameResultModel

interface DiceGameBetHistoryService {
    suspend fun getDiceGameBetHistory() : List<DiceGameResultModel>
}

package com.example.virtualsportsandroid.dices.data.api

import com.example.virtualsportsandroid.dices.data.models.GameResult

interface DiceBetHistoryService {
    suspend fun getDiceBetHistory() : List<GameResult>
}

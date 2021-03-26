package com.example.virtualsportsandroid.dices.history.data

import com.example.virtualsportsandroid.dices.game.data.DiceGameResultApiModel
import retrofit2.http.GET

interface DiceGameBetHistoryService {
    @GET("/User/history")
    suspend fun getDiceGameBetHistory(): List<DiceGameResultApiModel>
}

package com.example.virtualsportsandroid.dices.game.data

import retrofit2.http.Body
import retrofit2.http.POST

interface DiceGameResultService {
    @POST("/Games/play/dice")
    suspend fun getDiceGameResult(
        @Body diceGame: DiceGameApiModel
    ): DiceGameResultApiModel
}

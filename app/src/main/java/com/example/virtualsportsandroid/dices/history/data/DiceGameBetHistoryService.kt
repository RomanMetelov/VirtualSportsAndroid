package com.example.virtualsportsandroid.dices.history.data

import com.example.virtualsportsandroid.dices.DiceGameResultModel
import retrofit2.http.*

interface DiceGameBetHistoryService {
    @GET("/User/history")
    suspend fun getDiceGameBetHistory(

    )  : List<DiceGameResultModel>
}

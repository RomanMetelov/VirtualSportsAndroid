package com.example.virtualsportsandroid.dices.game.data

import com.example.virtualsportsandroid.dices.DiceGameResultModel
import retrofit2.http.*

interface DiceGameResultService {
    @POST("/Games/play/dice")
    @FormUrlEncoded
    suspend fun getDiceGameResult(
        @Field("datetime") datetime: String,
        @Field("betTypeId") betTypeId: Int
    ) : DiceGameResultModel
}

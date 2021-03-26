package com.example.virtualsportsandroid.dices.game.data

import com.example.virtualsportsandroid.dices.DiceGameResultModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface DiceGameResultService {
    @POST("/Games/play/dice")
    @FormUrlEncoded
    suspend fun getDiceGameResult(
        @Field("datetime") datetime: String,
        @Field("betTypeId") betTypeId: Int
    ) : DiceGameResultModel
}


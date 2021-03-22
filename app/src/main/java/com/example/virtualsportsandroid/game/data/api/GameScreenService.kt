package com.example.virtualsportsandroid.game.data.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GameScreenService {

    @POST("/addToFavorite")
    @FormUrlEncoded
    suspend fun addGameToFavorite(
        @Field("id") gameId: String,
    )

    @POST("/delFromFavorite")
    @FormUrlEncoded
    suspend fun delGameFromFavorite(
        @Field("id") gameId: String,
    )
}

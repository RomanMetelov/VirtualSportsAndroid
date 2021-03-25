@file:Suppress("ForbiddenComment")

package com.example.virtualsportsandroid.game.data.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface GameScreenService {

    @POST("/User/favourite/")
    suspend fun addGameToFavorite(
        @Path("gameId") gameId: String,
    )

    //TODO: change url when back add delete from favorite games
    @POST("/delFromFavorite")
    @FormUrlEncoded
    suspend fun delGameFromFavorite(
        @Field("id") gameId: String,
    )
}

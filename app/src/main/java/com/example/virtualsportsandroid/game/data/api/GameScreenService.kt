@file:Suppress("ForbiddenComment")

package com.example.virtualsportsandroid.game.data.api

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GameScreenService {

    @POST("/User/favourite/{gameId}")
    suspend fun addGameToFavorite(
        @Path("gameId") gameId: String,
    )

    @DELETE("/User/favourite/{gameId}")
    suspend fun delGameFromFavorite(
        @Path("gameId") gameId: String,
    )

    @GET("/Games/play/{gameId}")
    suspend fun playGame(
        @Path("gameId") gameId: String,
    )
}

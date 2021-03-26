package com.example.virtualsportsandroid.main.data

import retrofit2.http.GET

interface GamesInfoService {
    @GET("/Games")
    suspend fun loadGames(): GamesResponse

    @GET("/User/favourites")
    suspend fun loadFavorites(): List<GameResponse>

    @GET("/User/recent")
    suspend fun loadRecent(): List<GameResponse>
}
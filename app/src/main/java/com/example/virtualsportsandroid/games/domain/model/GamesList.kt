package com.example.virtualsportsandroid.games.domain.model

data class GamesList(
    var name: String,
    val games: MutableList<GameModel>
)

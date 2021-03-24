package com.example.virtualsportsandroid.mainScreen.domain.model

data class TagModel(
    val id: String,
    val name: String,
    val games: List<GameModel>
)

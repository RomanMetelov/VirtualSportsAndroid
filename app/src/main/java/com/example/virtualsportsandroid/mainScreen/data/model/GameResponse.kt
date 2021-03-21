package com.example.virtualsportsandroid.mainScreen.data.model

data class GameResponse(
    val id: String,
    val displayName: String,
    val imageURL: String,
    val providerId: String,
    val categoryIds: List<String>
)

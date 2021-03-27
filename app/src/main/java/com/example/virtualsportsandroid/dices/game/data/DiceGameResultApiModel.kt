package com.example.virtualsportsandroid.dices.game.data

import com.google.gson.annotations.SerializedName

data class DiceGameResultApiModel(
    @SerializedName("id") val id: String,
    @SerializedName("betType") val betType: Int,
    @SerializedName("droppedNumber") val droppedNumber: Int,
    @SerializedName("isBetWon") val isBetWon: Boolean,
    @SerializedName("dateTime") val datetime: String,
)

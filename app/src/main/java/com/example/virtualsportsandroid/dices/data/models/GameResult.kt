package com.example.virtualsportsandroid.dices.data.models

import com.google.gson.annotations.SerializedName

data class GameResult(
    @SerializedName("id") val id: Int,
    @SerializedName("betType") val betType: BetType,
    @SerializedName("droppedNumber") val droppedNumber: Int,
    @SerializedName("isBetWon") val isBetWon: Boolean
)

enum class BetType(val stringValue: String) {
    NUMBER1("1"),
    NUMBER2("2"),
    NUMBER3("3"),
    NUMBER4("4"),
    NUMBER5("5"),
    NUMBER6("6"),
    EVEN("Even"),
    ODD("Odd")

}

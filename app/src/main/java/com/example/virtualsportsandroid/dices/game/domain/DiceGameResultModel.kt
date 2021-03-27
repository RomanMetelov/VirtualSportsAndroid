package com.example.virtualsportsandroid.dices.game.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DiceGameResultModel(
    val id: String,
    val datetime: String,
    val betType: BetType,
    val droppedNumber: Int,
    val isBetWon: Boolean
) : Parcelable

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

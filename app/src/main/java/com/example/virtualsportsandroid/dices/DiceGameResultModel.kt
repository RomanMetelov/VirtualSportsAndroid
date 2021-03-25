package com.example.virtualsportsandroid.dices

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DiceGameResultModel(
    @SerializedName("id") val id: String,
    @SerializedName("datetime") val datetime: String,
    @SerializedName("betType") val betType: BetType,
    @SerializedName("droppedNumber") val droppedNumber: Int,
    @SerializedName("isBetWon") val isBetWon: Boolean
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

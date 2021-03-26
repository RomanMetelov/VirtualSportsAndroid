package com.example.virtualsportsandroid.dices.game.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class DiceGameResultApiModel(
    @SerializedName("id") val id: String,
    @SerializedName("betType") val betType: Int,
    @SerializedName("droppedNumber") val droppedNumber: Int,
    @SerializedName("isBetWon") val isBetWon: Boolean,
    @SerializedName("dateTime") val datetime: String,
)

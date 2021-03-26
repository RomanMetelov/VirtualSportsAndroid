package com.example.virtualsportsandroid.dices

import android.os.Parcelable
import com.example.virtualsportsandroid.dices.game.data.DiceGameResultApiModel
import com.example.virtualsportsandroid.dices.game.domain.DiceGameResultModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DiceGameBetHistoryModel(
    @SerializedName("betHistory") val betHistory: List<DiceGameResultModel>,
) : Parcelable

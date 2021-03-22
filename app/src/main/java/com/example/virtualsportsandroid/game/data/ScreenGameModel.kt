package com.example.virtualsportsandroid.game.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScreenGameModel(
    val id: String,
    val displayName: String,
    val url: String,
    var isFavorite: Boolean = false
) : Parcelable

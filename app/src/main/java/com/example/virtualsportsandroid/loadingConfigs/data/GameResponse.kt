package com.example.virtualsportsandroid.loadingConfigs.data

import com.google.gson.annotations.SerializedName

data class GameResponse(
    @SerializedName("id") val id: String,
    @SerializedName("displayName") val displayName: String,
    @SerializedName("provider") val providerId: String,
    @SerializedName("category") val categoriesIds: List<String>
)

package com.example.virtualsportsandroid.loadingConfigs.data

import com.google.gson.annotations.SerializedName

data class GameResponse(
    @SerializedName("id") val id: String,
    @SerializedName("displayName") val displayName: String,
    @SerializedName("provider") val providerId: String,
    @SerializedName("category") val categoriesIds: List<String>,
    @SerializedName("tag") val tagsIds: List<String>,
    @SerializedName("gameURL") val gameURL: String,
    @SerializedName("imageURL") val imageURL: String
)
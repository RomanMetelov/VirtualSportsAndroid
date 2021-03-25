package com.example.virtualsportsandroid.loadingConfigs.data

import com.google.gson.annotations.SerializedName

data class GameResponse(
    @SerializedName("id") val id: String,
    @SerializedName("displayName") val displayName: String,
    @SerializedName("provider") val providerId: String,
    @SerializedName("categories") val categoriesIds: List<String>,
    @SerializedName("tags") val tagsIds: List<String>,
    @SerializedName("url") val gameURL: String
)

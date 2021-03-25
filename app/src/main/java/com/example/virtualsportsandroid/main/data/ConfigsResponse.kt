package com.example.virtualsportsandroid.main.data

import com.google.gson.annotations.SerializedName

data class ConfigsResponse(
    @SerializedName("games") val games: List<GameResponse>,
    @SerializedName("categories") val categories: List<CategoryResponse>,
    @SerializedName("providers") val providers: List<ProviderResponse>,
    @SerializedName("tags") val tags: List<TagResponse>
)

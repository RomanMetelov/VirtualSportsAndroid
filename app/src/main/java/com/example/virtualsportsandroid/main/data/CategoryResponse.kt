package com.example.virtualsportsandroid.main.data

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("id") val id: String,
    @SerializedName("image") val imageURL: String,
    @SerializedName("displayName") val name: String
)

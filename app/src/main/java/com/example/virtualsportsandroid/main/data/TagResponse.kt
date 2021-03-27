package com.example.virtualsportsandroid.main.data

import com.google.gson.annotations.SerializedName

data class TagResponse(
    @SerializedName("id") val id: String,
    @SerializedName("displayName") val name: String
)

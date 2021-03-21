package com.example.virtualsportsandroid.login.data.model

import com.google.gson.annotations.SerializedName

data class AccessTokenResponse(
    @SerializedName("access_token") val accessToken: String?
)

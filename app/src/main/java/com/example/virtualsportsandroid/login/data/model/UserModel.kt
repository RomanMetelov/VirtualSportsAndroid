package com.example.virtualsportsandroid.login.data.model

import com.google.gson.annotations.SerializedName

data class UserModel(@SerializedName("login") val mail: String, val password: String)

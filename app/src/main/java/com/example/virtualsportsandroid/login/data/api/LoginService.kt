package com.example.virtualsportsandroid.login.data.api

import com.example.virtualsportsandroid.login.data.model.UserModel
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface LoginService {

    @POST("/login")
    suspend fun tryLoginAndGetAccessToken(
        @Body user: UserModel
    ): String

    @PUT("/logout")
    suspend fun logout()
}

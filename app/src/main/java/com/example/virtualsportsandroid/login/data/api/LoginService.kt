package com.example.virtualsportsandroid.login.data.api

import com.example.virtualsportsandroid.login.data.model.UserModel
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("/login")
    suspend fun tryLoginAndGetAccessToken(
        @Body user: UserModel
    ): String

}

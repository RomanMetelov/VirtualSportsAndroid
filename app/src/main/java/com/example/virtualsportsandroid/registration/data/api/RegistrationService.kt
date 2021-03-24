package com.example.virtualsportsandroid.registration.data.api

import com.example.virtualsportsandroid.login.data.model.AccessTokenResponse
import com.example.virtualsportsandroid.login.data.model.UserModel
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegistrationService {

    @POST("/register")
    suspend fun tryRegisterAndGetAccessToken(
        @Body user: UserModel
    ): String

}

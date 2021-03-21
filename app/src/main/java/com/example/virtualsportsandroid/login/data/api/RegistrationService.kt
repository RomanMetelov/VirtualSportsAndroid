package com.example.virtualsportsandroid.login.data.api

import com.example.virtualsportsandroid.login.data.model.AccessTokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegistrationService {

        @POST("/register")
        @FormUrlEncoded
        suspend fun tryRegisterAndGetAccessToken(
            @Field("login") login: String,
            @Field("password") password: String
        ): AccessTokenResponse

}

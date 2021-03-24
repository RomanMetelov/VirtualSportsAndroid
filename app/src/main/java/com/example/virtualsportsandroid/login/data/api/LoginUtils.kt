package com.example.virtualsportsandroid.login.data.api

import com.example.virtualsportsandroid.login.data.model.AccessTokenResponse
import com.example.virtualsportsandroid.login.data.model.UserModel
import com.example.virtualsportsandroid.utils.Result
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import com.example.virtualsportsandroid.utils.api.NetworkInterceptor
import javax.inject.Inject

class LoginUtils @Inject constructor(
    private val loginService: LoginService,
    private val networkInterceptor: NetworkInterceptor
) {
    @Suppress("TooGenericExceptionCaught")
    suspend fun tryLogin(user: UserModel): Result<AccessTokenResponse, NetworkErrorType> {
        return try {
            Result.success(
                AccessTokenResponse(
                    loginService.tryLoginAndGetAccessToken(
                        UserModel(
                            login = user.login,
                            password = user.password
                        )
                    )
                )
            )
        } catch (e: Exception) {
            networkInterceptor.getError(e)
        }
    }
}

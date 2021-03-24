package com.example.virtualsportsandroid.registration.data.api

import com.example.virtualsportsandroid.login.data.model.AccessTokenResponse
import com.example.virtualsportsandroid.login.data.model.UserModel
import com.example.virtualsportsandroid.utils.Result
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import com.example.virtualsportsandroid.utils.api.NetworkInterceptor
import javax.inject.Inject

class RegistrationUtils @Inject constructor(
    private val registrationService: RegistrationService,
    private val networkInterceptor: NetworkInterceptor
) {
    @Suppress("TooGenericExceptionCaught")
    suspend fun tryRegister(user: UserModel): Result<AccessTokenResponse, NetworkErrorType> {
        return try {
            Result.success(AccessTokenResponse(registrationService.tryRegisterAndGetAccessToken(user)))
        } catch (e: Exception) {
            networkInterceptor.getError(e)
        }
    }
}

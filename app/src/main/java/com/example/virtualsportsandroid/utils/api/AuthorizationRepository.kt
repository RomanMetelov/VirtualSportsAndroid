@file:Suppress("TooGenericExceptionCaught")

package com.example.virtualsportsandroid.utils.api

import com.example.virtualsportsandroid.login.data.api.LoginService
import com.example.virtualsportsandroid.login.data.model.AccessTokenResponse
import com.example.virtualsportsandroid.login.data.model.UserModel
import com.example.virtualsportsandroid.registration.data.api.RegistrationService
import com.example.virtualsportsandroid.utils.Result
import javax.inject.Inject

class AuthorizationRepository @Inject constructor(
    private val loginService: LoginService,
    private val registrationService: RegistrationService,
    private val networkExceptionHandler: NetworkExceptionHandler
) {

    suspend fun tryLogin(user: UserModel): Result<AccessTokenResponse, NetworkErrorType> {
        return try {
            Result.success(
                AccessTokenResponse(
                    loginService.tryLoginAndGetAccessToken(
                        UserModel(
                            mail = user.mail,
                            password = user.password
                        )
                    )
                )
            )
        } catch (e: Exception) {
            networkExceptionHandler.handleError(e)
        }
    }

    suspend fun tryRegister(user: UserModel): Result<AccessTokenResponse, NetworkErrorType> {
        return try {
            Result.success(AccessTokenResponse(registrationService.tryRegisterAndGetAccessToken(user)))
        } catch (e: Exception) {
            networkExceptionHandler.handleError(e)
        }
    }

    suspend fun logout(): Result<Boolean, NetworkErrorType> {
        return try {
            loginService.logout()
            Result.success(true)
        } catch (e: Exception) {
            networkExceptionHandler.handleError(e)
        }
    }
}

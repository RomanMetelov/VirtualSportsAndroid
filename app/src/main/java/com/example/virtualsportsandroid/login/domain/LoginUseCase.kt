package com.example.virtualsportsandroid.login.domain

import com.example.virtualsportsandroid.login.data.model.AccessTokenResponse
import com.example.virtualsportsandroid.login.data.model.UserModel
import com.example.virtualsportsandroid.utils.Result
import com.example.virtualsportsandroid.utils.api.AuthorizationRepository
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authorizationRepository: AuthorizationRepository) {

    suspend operator fun invoke(user: UserModel): Result<AccessTokenResponse, NetworkErrorType> {
        return authorizationRepository.tryLogin(user)
    }

}

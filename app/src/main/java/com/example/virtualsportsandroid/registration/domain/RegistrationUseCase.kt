package com.example.virtualsportsandroid.registration.domain

import com.example.virtualsportsandroid.login.data.model.AccessTokenResponse
import com.example.virtualsportsandroid.login.data.model.UserModel
import com.example.virtualsportsandroid.utils.Result
import com.example.virtualsportsandroid.utils.api.AuthorizationRepository
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(private val authorizationRepository: AuthorizationRepository) {

    suspend operator fun invoke(user: UserModel): Result<AccessTokenResponse, NetworkErrorType> {
        return authorizationRepository.tryRegister(user)
    }
}

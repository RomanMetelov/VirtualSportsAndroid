package com.example.virtualsportsandroid.registration.domain

import com.example.virtualsportsandroid.registration.data.api.RegistrationErrorType
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import javax.inject.Inject

class NetworkToRegisterErrorsMapper @Inject constructor() {

    fun invoke(networkError: NetworkErrorType): RegistrationErrorType {
        return when (networkError) {
            NetworkErrorType.USER_EXIST -> RegistrationErrorType.USER_EXIST
            NetworkErrorType.BAD_REQUEST -> RegistrationErrorType.INPUTS_ERROR
            NetworkErrorType.NO_NETWORK -> RegistrationErrorType.NETWORK_ERROR
            else -> RegistrationErrorType.UNKNOWN_ERROR
        }
    }
}

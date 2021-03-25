package com.example.virtualsportsandroid.login.domain

import com.example.virtualsportsandroid.login.data.api.LoginErrorType
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import javax.inject.Inject

class NetworkToLoginErrorsMapper @Inject constructor() {

    fun invoke(networkError: NetworkErrorType): LoginErrorType {
        return when (networkError) {
            NetworkErrorType.RESOURCE_NOT_FOUND -> LoginErrorType.LOGIN_ERROR
            NetworkErrorType.BAD_REQUEST -> LoginErrorType.INPUTS_ERROR
            NetworkErrorType.NO_NETWORK -> LoginErrorType.NETWORK_ERROR
            else -> LoginErrorType.UNKNOWN_ERROR
        }
    }
}

package com.example.virtualsportsandroid.login.domain

import androidx.annotation.StringRes
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import javax.inject.Inject

enum class LoginErrorType(@StringRes val errorMessage: Int) {
    INPUTS_ERROR(R.string.inputs_login_error_text),
    LOGIN_ERROR(R.string.user_not_found_login_error_text),
    NETWORK_ERROR(R.string.network_error_text),
    UNKNOWN_ERROR(R.string.unknown_login_error_text)
}

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

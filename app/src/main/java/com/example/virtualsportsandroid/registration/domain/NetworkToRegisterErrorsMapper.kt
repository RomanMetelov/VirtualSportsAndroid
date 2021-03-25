package com.example.virtualsportsandroid.registration.domain

import androidx.annotation.StringRes
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import javax.inject.Inject

enum class RegistrationErrorType(@StringRes val errorMessage: Int) {
    USER_EXIST(R.string.user_exist_registration_error_text),
    INPUTS_ERROR(R.string.inputs_registration_error_text),
    NETWORK_ERROR(R.string.network_error_text),
    UNKNOWN_ERROR(R.string.unknown_registration_error_text)
}

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

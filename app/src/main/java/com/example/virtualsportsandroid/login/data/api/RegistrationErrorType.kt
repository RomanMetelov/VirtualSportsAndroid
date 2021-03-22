package com.example.virtualsportsandroid.login.data.api

import androidx.annotation.StringRes
import com.example.virtualsportsandroid.R

enum class RegistrationErrorType(@StringRes val errorMessage: Int) {
    USER_EXIST(R.string.user_exist_registration_error_text),
    INPUTS_ERROR(R.string.inputs_registration_error_text),
    NETWORK_ERROR(R.string.network_error_text),
    UNKNOWN_ERROR(R.string.unknown_registration_error_text)
}

package com.example.virtualsportsandroid.login.data.api

import androidx.annotation.StringRes
import com.example.virtualsportsandroid.R


enum class LoginErrorType(@StringRes val errorMessage: Int) {
    INPUTS_ERROR(R.string.inputs_login_error_text),
    LOGIN_ERROR(R.string.user_not_found_login_error_text),
    NETWORK_ERROR(R.string.network_error_text),
    UNKNOWN_ERROR(R.string.unknown_login_error_text)
}

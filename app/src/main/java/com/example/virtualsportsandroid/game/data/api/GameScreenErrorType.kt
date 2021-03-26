package com.example.virtualsportsandroid.game.data.api

import androidx.annotation.StringRes
import com.example.virtualsportsandroid.R

enum class GameScreenErrorType(@StringRes val errorMessage: Int) {
    NETWORK_ERROR(R.string.network_error_text),
    UNKNOWN_ERROR(R.string.unknown_registration_error_text),
    UNAUTHORIZED(R.string.need_authorization_error_text)
}

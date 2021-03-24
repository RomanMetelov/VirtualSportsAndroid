package com.example.virtualsportsandroid.login.domain

import androidx.annotation.StringRes
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.login.data.model.UserModel
import com.example.virtualsportsandroid.utils.Result
import javax.inject.Inject

enum class LoginInputsErrorType(@StringRes val messageError: Int) {
    EMPTY_LOGIN(R.string.empty_login_error),
    EMPTY_PASSWORD(R.string.empty_password_error)
}

data class LoginInputsError(val type: LoginInputsErrorType, val requireValue: String = "")

class CheckLoginInputsUseCase @Inject constructor() {
    companion object {
        private const val minLoginLength = 6
        private const val minPasswordLength = 8
    }

    fun invoke(
        user: UserModel
    ): Result<Boolean, LoginInputsError> {
        return when {
            user.login.isEmpty() -> {
                Result.error(
                    LoginInputsError(
                        LoginInputsErrorType.EMPTY_LOGIN,
                        minLoginLength.toString()
                    )
                )
            }
            user.password.isEmpty() -> {
                Result.error(
                    LoginInputsError(
                        LoginInputsErrorType.EMPTY_PASSWORD,
                        minPasswordLength.toString()
                    )
                )
            }
            else -> Result.success(true)
        }
    }
}

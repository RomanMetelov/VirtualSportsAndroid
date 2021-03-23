package com.example.virtualsportsandroid.login.domain

import androidx.annotation.StringRes
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.utils.Result
import javax.inject.Inject

enum class RegistrationInputsErrorType(@StringRes val messageError: Int) {
    MIN_LOGIN_LENGTH(R.string.min_login_length_error),
    MAX_LOGIN_LENGTH(R.string.max_login_length_error),
    MIN_PASSWORD_LENGTH(R.string.min_password_length_error),
    MAX_PASSWORD_LENGTH(R.string.max_password_length_error),
    NOT_SAME_PASSWORD(R.string.not_same_password_error)
}

data class RegistrationInputsError(
    val type: RegistrationInputsErrorType,
    val requireValue: String = ""
)

class CheckRegistrationInputsUseCase @Inject constructor() {

    companion object {
        private const val minLoginLength = 3
        private const val maxLoginLength = 15
        private const val minPasswordLength = 8
        private const val maxPasswordLength = 20
    }

    fun invoke(
        login: String,
        password: String,
        repeatPassword: String
    ): Result<Boolean, RegistrationInputsError> {

        return when {
            login.length < minLoginLength -> {
                Result.error(
                    RegistrationInputsError(
                        RegistrationInputsErrorType.MIN_LOGIN_LENGTH,
                        minLoginLength.toString()
                    )
                )
            }
            login.length > maxLoginLength -> {
                Result.error(
                    RegistrationInputsError(
                        RegistrationInputsErrorType.MAX_LOGIN_LENGTH,
                        maxLoginLength.toString()
                    )
                )
            }
            password.length < minPasswordLength -> {
                Result.error(
                    RegistrationInputsError(
                        RegistrationInputsErrorType.MIN_PASSWORD_LENGTH,
                        minPasswordLength.toString()
                    )
                )
            }
            password.length > maxPasswordLength -> {
                Result.error(
                    RegistrationInputsError(
                        RegistrationInputsErrorType.MAX_PASSWORD_LENGTH,
                        maxPasswordLength.toString()
                    )
                )
            }
            password != repeatPassword -> {
                Result.error(
                    RegistrationInputsError(
                        RegistrationInputsErrorType.NOT_SAME_PASSWORD
                    )
                )
            }
            else -> Result.success(true)
        }
    }


}
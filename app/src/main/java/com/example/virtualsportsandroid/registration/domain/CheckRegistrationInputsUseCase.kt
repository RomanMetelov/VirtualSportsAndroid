package com.example.virtualsportsandroid.registration.domain

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

data class RegistrationUserInputs(
    val login: String,
    val password: String,
    val repeatPassword: String
)

data class RegistrationInputsError(
    val type: RegistrationInputsErrorType,
    val requireValue: String = ""
)

class CheckRegistrationInputsUseCase @Inject constructor() {

    companion object {
        private const val minLoginLength = 3
        private const val maxLoginLength = 64
        private const val minPasswordLength = 8
        private const val maxPasswordLength = 20
    }

    fun invoke(userInputs: RegistrationUserInputs): Result<Boolean, RegistrationInputsError> {
        return when {
            userInputs.login.length < minLoginLength -> {
                Result.error(
                    RegistrationInputsError(
                        RegistrationInputsErrorType.MIN_LOGIN_LENGTH,
                        minLoginLength.toString()
                    )
                )
            }
            userInputs.login.length > maxLoginLength -> {
                Result.error(
                    RegistrationInputsError(
                        RegistrationInputsErrorType.MAX_LOGIN_LENGTH,
                        maxLoginLength.toString()
                    )
                )
            }
            userInputs.password.length < minPasswordLength -> {
                Result.error(
                    RegistrationInputsError(
                        RegistrationInputsErrorType.MIN_PASSWORD_LENGTH,
                        minPasswordLength.toString()
                    )
                )
            }
            userInputs.password.length > maxPasswordLength -> {
                Result.error(
                    RegistrationInputsError(
                        RegistrationInputsErrorType.MAX_PASSWORD_LENGTH,
                        maxPasswordLength.toString()
                    )
                )
            }
            userInputs.password != userInputs.repeatPassword -> {
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

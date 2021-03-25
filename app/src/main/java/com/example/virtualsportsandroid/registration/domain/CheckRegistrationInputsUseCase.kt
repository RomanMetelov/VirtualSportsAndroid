package com.example.virtualsportsandroid.registration.domain

import androidx.annotation.StringRes
import androidx.core.util.PatternsCompat
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.utils.Result
import javax.inject.Inject

enum class RegistrationInputsErrorType(@StringRes val messageError: Int) {
    MIN_MAIL_LENGTH(R.string.min_mail_length_error),
    MAX_MAIL_LENGTH(R.string.max_mail_length_error),
    MIN_PASSWORD_LENGTH(R.string.min_password_length_error),
    MAX_PASSWORD_LENGTH(R.string.max_password_length_error),
    NOT_SAME_PASSWORD(R.string.not_same_password_error),
    INCORRECT_MAIL(R.string.incorrect_mail_error)
}

data class RegistrationUserInputs(
    val mail: String,
    val password: String,
    val repeatPassword: String
)

data class RegistrationInputsError(
    val type: RegistrationInputsErrorType,
    val requireValue: String = ""
)

class CheckRegistrationInputsUseCase @Inject constructor() {

    companion object {
        private const val minMailLength = 6
        private const val maxMailLength = 64
        private const val minPasswordLength = 8
        private const val maxPasswordLength = 20
    }

    operator fun invoke(userInputs: RegistrationUserInputs): Result<Boolean, RegistrationInputsError> {
        return when {
            !PatternsCompat.EMAIL_ADDRESS.matcher(userInputs.mail).matches() -> {
                Result.error(
                    RegistrationInputsError(
                        RegistrationInputsErrorType.INCORRECT_MAIL
                    )
                )
            }
            userInputs.mail.length < minMailLength -> {
                Result.error(
                    RegistrationInputsError(
                        RegistrationInputsErrorType.MIN_MAIL_LENGTH,
                        minMailLength.toString()
                    )
                )
            }
            userInputs.mail.length > maxMailLength -> {
                Result.error(
                    RegistrationInputsError(
                        RegistrationInputsErrorType.MAX_MAIL_LENGTH,
                        maxMailLength.toString()
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

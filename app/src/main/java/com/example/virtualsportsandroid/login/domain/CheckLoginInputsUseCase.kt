package com.example.virtualsportsandroid.login.domain

import androidx.annotation.StringRes
import androidx.core.util.PatternsCompat
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.login.data.model.UserModel
import com.example.virtualsportsandroid.utils.Result
import javax.inject.Inject

enum class LoginInputsErrorType(@StringRes val messageError: Int) {
    INCORRECT_MAIL(R.string.incorrect_mail_error),
    EMPTY_MAIL(R.string.empty_mail_error),
    EMPTY_PASSWORD(R.string.empty_password_error)
}

data class LoginInputsError(val type: LoginInputsErrorType, val requireValue: String = "")

class CheckLoginInputsUseCase @Inject constructor() {
    companion object {
        private const val minLoginLength = 6
        private const val minPasswordLength = 8
    }

    operator fun invoke(user: UserModel): Result<Boolean, LoginInputsError> {
        return when {
            user.mail.isEmpty() -> {
                Result.error(
                    LoginInputsError(
                        LoginInputsErrorType.EMPTY_MAIL,
                        minLoginLength.toString()
                    )
                )
            }
            !PatternsCompat.EMAIL_ADDRESS.matcher(user.mail).matches() -> {
                Result.error(
                    LoginInputsError(
                        LoginInputsErrorType.INCORRECT_MAIL
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

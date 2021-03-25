package com.example.virtualsportsandroid

import com.example.virtualsportsandroid.registration.domain.CheckRegistrationInputsUseCase
import com.example.virtualsportsandroid.registration.domain.RegistrationInputsErrorType
import com.example.virtualsportsandroid.registration.domain.RegistrationUserInputs
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class RegistrationInputsErrorTest {

    @Test
    fun `if login too small - should return error result min login length`() {
        val useCase = CheckRegistrationInputsUseCase()
        val login = "12"
        val emptyPassword = ""
        val emptyRepeatPassword = ""

        useCase.invoke(
            RegistrationUserInputs(
                login = login,
                password = emptyPassword,
                repeatPassword = emptyRepeatPassword
            )
        ).errorResult.type shouldBe RegistrationInputsErrorType.MIN_LOGIN_LENGTH
    }

    @Test
    fun `if password too small - should return error result min password length`() {
        val useCase = CheckRegistrationInputsUseCase()
        val login = "login"
        val password = "123"
        val repeatPassword = "123"

        useCase.invoke(
            RegistrationUserInputs(
                login = login,
                password = password,
                repeatPassword = repeatPassword
            )
        ).errorResult.type shouldBe RegistrationInputsErrorType.MIN_PASSWORD_LENGTH
    }

    @Test
    fun `if repeat password not the same with password - should return error type no same password`() {
        val useCase = CheckRegistrationInputsUseCase()
        val login = "login"
        val password = "12345678"
        val repeatPassword = "87654321"

        useCase.invoke(
            RegistrationUserInputs(
                login = login,
                password = password,
                repeatPassword = repeatPassword
            )
        ).errorResult.type shouldBe RegistrationInputsErrorType.NOT_SAME_PASSWORD
    }

    @Test
    fun `if login too long - should return error result max login length`() {
        val useCase = CheckRegistrationInputsUseCase()
        val login = "123456789123456789123456789123456789123456789123456789123456789123456789"
        val emptyPassword = ""
        val emptyRepeatPassword = ""

        useCase.invoke(
            RegistrationUserInputs(
                login = login,
                password = emptyPassword,
                repeatPassword = emptyRepeatPassword
            )
        ).errorResult.type shouldBe RegistrationInputsErrorType.MAX_LOGIN_LENGTH
    }

    @Test
    fun `if password too long - should return error result max password length`() {
        val useCase = CheckRegistrationInputsUseCase()
        val login = "login"
        val password = "123456789123456789123456789"
        val repeatPassword = "123456789123456789123456789"

        useCase.invoke(
            RegistrationUserInputs(
                login = login,
                password = password,
                repeatPassword = repeatPassword
            )
        ).errorResult.type shouldBe RegistrationInputsErrorType.MAX_PASSWORD_LENGTH
    }
}
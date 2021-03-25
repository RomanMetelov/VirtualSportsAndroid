package com.example.virtualsportsandroid

import com.example.virtualsportsandroid.registration.domain.CheckRegistrationInputsUseCase
import com.example.virtualsportsandroid.registration.domain.RegistrationInputsErrorType
import com.example.virtualsportsandroid.registration.domain.RegistrationUserInputs
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class RegistrationInputsErrorTest {

    @Test
    fun `if mail too small - should return error result min mail length`() {
        val useCase = CheckRegistrationInputsUseCase()
        val mail = "a@a.c"
        val emptyPassword = ""
        val emptyRepeatPassword = ""

        useCase.invoke(
            RegistrationUserInputs(
                mail = mail,
                password = emptyPassword,
                repeatPassword = emptyRepeatPassword
            )
        ).errorResult.type shouldBe RegistrationInputsErrorType.MIN_MAIL_LENGTH
    }

    @Test
    fun `if password too small - should return error result min password length`() {
        val useCase = CheckRegistrationInputsUseCase()
        val mail = "mail@example.comf"
        val password = "123"
        val repeatPassword = "123"

        useCase.invoke(
            RegistrationUserInputs(
                mail = mail,
                password = password,
                repeatPassword = repeatPassword
            )
        ).errorResult.type shouldBe RegistrationInputsErrorType.MIN_PASSWORD_LENGTH
    }

    @Test
    fun `if repeat password not the same with password - should return error type no same password`() {
        val useCase = CheckRegistrationInputsUseCase()
        val mail = "mail@mail.com"
        val password = "12345678"
        val repeatPassword = "87654321"

        useCase.invoke(
            RegistrationUserInputs(
                mail = mail,
                password = password,
                repeatPassword = repeatPassword
            )
        ).errorResult.type shouldBe RegistrationInputsErrorType.NOT_SAME_PASSWORD
    }

    @Test
    fun `if mail too long - should return error result max mail length`() {
        val useCase = CheckRegistrationInputsUseCase()
        val mail = "1234567891234567891234567891234567891234567891234567891234567891@gmail.com"
        val emptyPassword = ""
        val emptyRepeatPassword = ""

        useCase.invoke(
            RegistrationUserInputs(
                mail = mail,
                password = emptyPassword,
                repeatPassword = emptyRepeatPassword
            )
        ).errorResult.type shouldBe RegistrationInputsErrorType.MAX_MAIL_LENGTH
    }

    @Test
    fun `if password too long - should return error result max password length`() {
        val useCase = CheckRegistrationInputsUseCase()
        val mail = "mail@mail.com"
        val password = "123456789123456789123456789"
        val repeatPassword = "123456789123456789123456789"

        useCase.invoke(
            RegistrationUserInputs(
                mail = mail,
                password = password,
                repeatPassword = repeatPassword
            )
        ).errorResult.type shouldBe RegistrationInputsErrorType.MAX_PASSWORD_LENGTH
    }
}
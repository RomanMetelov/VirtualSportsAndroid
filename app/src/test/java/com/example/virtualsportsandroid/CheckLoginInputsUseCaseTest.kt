package com.example.virtualsportsandroid

import com.example.virtualsportsandroid.login.data.model.UserModel
import com.example.virtualsportsandroid.login.domain.CheckLoginInputsUseCase
import com.example.virtualsportsandroid.login.domain.LoginInputsErrorType
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class CheckLoginInputsUseCaseTest {

    @Test
    fun `if mail and pass empty - should return error result`() {
        val useCase = CheckLoginInputsUseCase()
        val emptyMail = ""
        val emptyPassword = ""

        useCase(
            UserModel(
                mail = emptyMail,
                password = emptyPassword
            )
        ).isError shouldBe true
    }

    @Test
    fun `if mail empty - should return error result empty mail`() {
        val useCase = CheckLoginInputsUseCase()
        val emptyMail = ""
        val password = "12345"

        useCase(
            UserModel(
                mail = emptyMail,
                password = password
            )
        ).errorResult.type shouldBe LoginInputsErrorType.EMPTY_MAIL
    }

    @Test
    fun `if password empty - should return error result password login`() {
        val useCase = CheckLoginInputsUseCase()
        val mail = "dassd@gmail.com"
        val emptyPassword = ""

        useCase(
            UserModel(
                mail = mail,
                password = emptyPassword
            )
        ).errorResult.type shouldBe LoginInputsErrorType.EMPTY_PASSWORD
    }

    @Test
    fun `if mail incorrect - should return error result incorrect mail`() {
        val useCase = CheckLoginInputsUseCase()
        val emptyMail = "incorrect mail"
        val password = "12345678"

        useCase(
            UserModel(
                mail = emptyMail,
                password = password
            )
        ).errorResult.type shouldBe LoginInputsErrorType.INCORRECT_MAIL
    }

}

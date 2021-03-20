package com.example.virtualsportsandroid

import com.example.virtualsportsandroid.login.domain.CheckLoginInputsUseCase
import com.example.virtualsportsandroid.login.domain.LoginInputsErrorType
import io.kotest.matchers.shouldBe
import org.junit.Test


class CheckLoginInputsUseCaseTest {

    @Test
    fun `if login and pass empty - should return error result`() {
        val useCase = CheckLoginInputsUseCase()
        val emptyLogin = ""
        val emptyPassword = ""

        useCase.invoke(login = emptyLogin, password = emptyPassword).isError shouldBe true
    }

    @Test
    fun `if login empty - should return error result empty login`() {
        val useCase = CheckLoginInputsUseCase()
        val emptyLogin = ""
        val password = "12345"

        useCase.invoke(
            login = emptyLogin,
            password = password
        ).errorResult.type shouldBe LoginInputsErrorType.EMPTY_LOGIN
    }

    @Test
    fun `if password empty - should return error result password login`() {
        val useCase = CheckLoginInputsUseCase()
        val login = "12345"
        val emptyPassword = ""

        useCase.invoke(
            login = login,
            password = emptyPassword
        ).errorResult.type shouldBe LoginInputsErrorType.EMPTY_LOGIN
    }

}

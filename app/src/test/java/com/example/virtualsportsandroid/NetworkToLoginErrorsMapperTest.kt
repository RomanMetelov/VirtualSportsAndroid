package com.example.virtualsportsandroid

import com.example.virtualsportsandroid.login.data.api.LoginErrorType
import com.example.virtualsportsandroid.login.domain.NetworkToLoginErrorsMapper
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class NetworkToLoginErrorsMapperTest {

    @Test
    fun `should correct map from network error to login error USER_NOT_FOUND`(){

        val mapper = NetworkToLoginErrorsMapper()
        val networkError = NetworkErrorType.RESOURCE_NOT_FOUND
        val expectedResult = LoginErrorType.LOGIN_ERROR

        mapper.invoke(networkError) shouldBe expectedResult
    }

    @Test
    fun `should correct map from network error to login error NETWORK_ERROR`(){

        val mapper = NetworkToLoginErrorsMapper()
        val networkError = NetworkErrorType.NO_NETWORK
        val expectedResult = LoginErrorType.NETWORK_ERROR

        mapper.invoke(networkError) shouldBe expectedResult
    }

    @Test
    fun `should correct map from network error to login error INPUTS_ERROR`(){

        val mapper = NetworkToLoginErrorsMapper()
        val networkError = NetworkErrorType.BAD_REQUEST
        val expectedResult = LoginErrorType.INPUTS_ERROR

        mapper.invoke(networkError) shouldBe expectedResult
    }

    @Test
    fun `should correct map from network error to login error UNKNOWN_ERROR`(){

        val mapper = NetworkToLoginErrorsMapper()
        val networkError = NetworkErrorType.UNKNOWN_ERROR
        val expectedResult = LoginErrorType.UNKNOWN_ERROR

        mapper.invoke(networkError) shouldBe expectedResult
    }

}

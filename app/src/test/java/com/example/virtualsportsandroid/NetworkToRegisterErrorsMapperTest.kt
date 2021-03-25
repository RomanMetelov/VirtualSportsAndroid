package com.example.virtualsportsandroid

import com.example.virtualsportsandroid.registration.domain.NetworkToRegisterErrorsMapper
import com.example.virtualsportsandroid.registration.domain.RegistrationErrorType
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class NetworkToRegisterErrorsMapperTest {

    @Test
    fun `should correct map from network error to register error USER_EXIST`() {

        val mapper = NetworkToRegisterErrorsMapper()
        val networkError = NetworkErrorType.USER_EXIST
        val expectedResult = RegistrationErrorType.USER_EXIST

        mapper.invoke(networkError) shouldBe expectedResult
    }

    @Test
    fun `should correct map from network error to register error NETWORK_ERROR`() {

        val mapper = NetworkToRegisterErrorsMapper()
        val networkError = NetworkErrorType.NO_NETWORK
        val expectedResult = RegistrationErrorType.NETWORK_ERROR

        mapper.invoke(networkError) shouldBe expectedResult
    }

    @Test
    fun `should correct map from network error to register error INPUTS_ERROR`() {

        val mapper = NetworkToRegisterErrorsMapper()
        val networkError = NetworkErrorType.BAD_REQUEST
        val expectedResult = RegistrationErrorType.INPUTS_ERROR

        mapper.invoke(networkError) shouldBe expectedResult
    }

    @Test
    fun `should correct map from network error to register error UNKNOWN_ERROR`() {

        val mapper = NetworkToRegisterErrorsMapper()
        val networkError = NetworkErrorType.UNKNOWN_ERROR
        val expectedResult = RegistrationErrorType.UNKNOWN_ERROR

        mapper.invoke(networkError) shouldBe expectedResult
    }

}
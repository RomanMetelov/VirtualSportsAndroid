package com.example.virtualsportsandroid

import com.example.virtualsportsandroid.game.data.api.GameScreenErrorType
import com.example.virtualsportsandroid.game.domain.NetworkToGameScreenErrorMapper
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class NetworkToGameScreenErrorMapperTest {

    @Test
    fun `should correct map from network error to game screen error NETWORK_ERROR`() {

        val mapper = NetworkToGameScreenErrorMapper()
        val networkError = NetworkErrorType.NO_NETWORK
        val expectedResult = GameScreenErrorType.NETWORK_ERROR

        mapper.invoke(networkError) shouldBe expectedResult
    }

    @Test
    fun `should correct map from network error to game screen error UNKNOWN_ERROR`() {

        val mapper = NetworkToGameScreenErrorMapper()
        val networkError = NetworkErrorType.UNKNOWN_ERROR
        val expectedResult = GameScreenErrorType.UNKNOWN_ERROR

        mapper.invoke(networkError) shouldBe expectedResult
    }
}

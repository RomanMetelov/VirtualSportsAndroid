package com.example.virtualsportsandroid

import com.example.virtualsportsandroid.dices.game.data.DiceGameResultRepository
import com.example.virtualsportsandroid.dices.game.domain.DiceGameResultLoadingUseCase
import com.example.virtualsportsandroid.dices.game.domain.FromApiToUiMapper
import com.example.virtualsportsandroid.dices.game.ui.DiceGameResultFragmentState
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

class DiceGameResultLoadingUseCaseTest {

    @ExperimentalCoroutinesApi
    @Test
    fun `loading diceroll result without error works correctly`() {
        val mockkDiceGameResultRepository = mockk<DiceGameResultRepository>()
        val mockkFromApiToUiMapper = mockk<FromApiToUiMapper>()
        val correctBetType = 1

        runBlockingTest {
            DiceGameResultLoadingUseCase(
                TestCoroutineDispatcher(),
                mockkDiceGameResultRepository,
                mockkFromApiToUiMapper
            ).invoke(correctBetType, "datetime") shouldBe DiceGameResultFragmentState.Content(
                mockkDiceGameResultRepository.getDiceGameResult("datetime", correctBetType)
                    .mapSuccess { mockkFromApiToUiMapper(it) }.successResult
            )
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `loading diceroll result with error works correctly`() {
        val mockkDiceGameResultRepository = mockk<DiceGameResultRepository>()
        val mockkFromApiToUiMapper = mockk<FromApiToUiMapper>()
        val wrongBetType = 9

        runBlockingTest {
            DiceGameResultLoadingUseCase(
                TestCoroutineDispatcher(),
                mockkDiceGameResultRepository,
                mockkFromApiToUiMapper
            ).invoke(wrongBetType, "datetime") shouldBe DiceGameResultFragmentState.Error(
                mockkDiceGameResultRepository.getDiceGameResult("datetime", wrongBetType)
                    .mapSuccess { mockkFromApiToUiMapper(it) }.errorResult
            )
        }
    }
}
package com.example.virtualsportsandroid

import com.example.virtualsportsandroid.dices.history.data.DiceGameBetHistoryRepository
import com.example.virtualsportsandroid.dices.history.domain.DiceGameBetHistoryLoadingUseCase
import com.example.virtualsportsandroid.dices.history.ui.DiceGameBetHistoryFragmentState
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

class DiceGameBetHistoryLoadingUseCaseTest {

    @ExperimentalCoroutinesApi
    @Test
    fun `loading diceroll bet history result without error works correctly`() {
        val mockkDiceGameBetHistoryRepository = mockk<DiceGameBetHistoryRepository>()

        runBlockingTest {
            DiceGameBetHistoryLoadingUseCase(
                TestCoroutineDispatcher(),
                mockkDiceGameBetHistoryRepository,
            ).invoke() shouldBe DiceGameBetHistoryFragmentState.Content(
                mockkDiceGameBetHistoryRepository.getDiceGameBetHistory().successResult
            )
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `loading diceroll bet history result with error works correctly`() {
        val mockkDiceGameBetHistoryRepository = mockk<DiceGameBetHistoryRepository>()

        runBlockingTest {
            DiceGameBetHistoryLoadingUseCase(
                TestCoroutineDispatcher(),
                mockkDiceGameBetHistoryRepository,
            ).invoke() shouldBe DiceGameBetHistoryFragmentState.Error(
                mockkDiceGameBetHistoryRepository.getDiceGameBetHistory().errorResult
            )
        }
    }

}
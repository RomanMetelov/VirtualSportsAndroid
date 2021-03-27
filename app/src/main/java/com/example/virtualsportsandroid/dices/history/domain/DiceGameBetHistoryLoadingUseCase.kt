package com.example.virtualsportsandroid.dices.history.domain

import com.example.virtualsportsandroid.dices.history.ui.DiceGameBetHistoryFragmentState
import com.example.virtualsportsandroid.dices.history.data.DiceGameBetHistoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DiceGameBetHistoryLoadingUseCase @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val diceGameBetHistoryRepository: DiceGameBetHistoryRepository
) {
    suspend operator fun invoke(): DiceGameBetHistoryFragmentState = withContext(dispatcher) {
        val betHistoryResult = diceGameBetHistoryRepository.getDiceGameBetHistory()
        when {
            betHistoryResult.isError -> DiceGameBetHistoryFragmentState.Error(betHistoryResult.errorResult)
            else -> DiceGameBetHistoryFragmentState.Content(
                betHistoryResult.successResult
            )
        }
    }
}

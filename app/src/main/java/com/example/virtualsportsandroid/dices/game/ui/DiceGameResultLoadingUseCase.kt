package com.example.virtualsportsandroid.dices.game.ui

import com.example.virtualsportsandroid.dices.game.DiceGameResultFragmentState
import com.example.virtualsportsandroid.dices.game.data.DiceGameResultRepository
import com.example.virtualsportsandroid.dices.history.DiceGameBetHistoryFragmentState
import com.example.virtualsportsandroid.dices.history.data.DiceGameBetHistoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DiceGameResultLoadingUseCase @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val diceGameResultRepository: DiceGameResultRepository
) {
    suspend operator fun invoke(betTypeId: Int, datetime: String): DiceGameResultFragmentState = withContext(dispatcher) {
        val betResult = diceGameResultRepository.getDiceGameResult(betTypeId, datetime)
        when {
            betResult.isError -> DiceGameResultFragmentState.Error(betResult.errorResult)
            else -> DiceGameResultFragmentState.Content(
                betResult.successResult
            )
        }
    }
}

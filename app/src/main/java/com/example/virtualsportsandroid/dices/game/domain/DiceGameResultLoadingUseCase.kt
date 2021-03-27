package com.example.virtualsportsandroid.dices.game.domain

import com.example.virtualsportsandroid.dices.game.data.DiceGameResultRepository
import com.example.virtualsportsandroid.dices.game.ui.DiceGameResultFragmentState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DiceGameResultLoadingUseCase @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val diceGameResultRepository: DiceGameResultRepository,
    private val fromApiToUiMapper: FromApiToUiMapper
) {
    suspend operator fun invoke(
        betTypeId: Int,
        datetime: String
    ): DiceGameResultFragmentState = withContext(dispatcher) {
        val betResult = diceGameResultRepository.getDiceGameResult(datetime, betTypeId)
            .mapSuccess { fromApiToUiMapper(it) }
        when {
            betResult.isError -> DiceGameResultFragmentState.Error(betResult.errorResult)
            else -> DiceGameResultFragmentState.Content(
                betResult.successResult
            )
        }
    }
}

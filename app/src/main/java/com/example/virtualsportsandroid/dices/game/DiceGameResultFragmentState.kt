package com.example.virtualsportsandroid.dices.game

import com.example.virtualsportsandroid.dices.game.data.DiceGameResultApiModel
import com.example.virtualsportsandroid.dices.game.domain.DiceGameResultModel
import com.example.virtualsportsandroid.utils.api.NetworkErrorType

sealed class DiceGameResultFragmentState {
    object Loading : DiceGameResultFragmentState()
    data class Content(
        val gameResultApi: DiceGameResultModel,
    ) : DiceGameResultFragmentState()

    data class Error(val errorMessage: NetworkErrorType) : DiceGameResultFragmentState()
}

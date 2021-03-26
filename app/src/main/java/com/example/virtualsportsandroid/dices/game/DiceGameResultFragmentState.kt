package com.example.virtualsportsandroid.dices.game

import com.example.virtualsportsandroid.dices.DiceGameResultModel
import com.example.virtualsportsandroid.utils.api.NetworkErrorType

sealed class DiceGameResultFragmentState {
    object Loading : DiceGameResultFragmentState()
    data class Content(
        val gameResult: DiceGameResultModel,
    ) : DiceGameResultFragmentState()

    data class Error(val errorMessage: NetworkErrorType) : DiceGameResultFragmentState()
}

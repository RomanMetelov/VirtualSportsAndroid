package com.example.virtualsportsandroid.dices.game

import com.example.virtualsportsandroid.dices.DiceGameResultModel

sealed class DiceGameResultFragmentState {
    object Loading : DiceGameResultFragmentState()
    data class Content(
        val gameResult: DiceGameResultModel,
    ) : DiceGameResultFragmentState()

    data class Error(val errorMessage: String) : DiceGameResultFragmentState()
}

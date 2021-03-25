package com.example.virtualsportsandroid.dices.history

import com.example.virtualsportsandroid.dices.DiceGameResultModel

sealed class DiceGameBetHistoryFragmentState {
    object Loading : DiceGameBetHistoryFragmentState()
    data class Content(
        val last100Bets: List<DiceGameResultModel>,
    ) : DiceGameBetHistoryFragmentState()

    data class Error(val errorMessage: String) : DiceGameBetHistoryFragmentState()
}

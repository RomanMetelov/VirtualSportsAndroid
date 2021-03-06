package com.example.virtualsportsandroid.dices.history.ui

import com.example.virtualsportsandroid.dices.game.domain.DiceGameResultModel

sealed class DiceGameBetHistoryFragmentState {
    object Loading : DiceGameBetHistoryFragmentState()
    data class Content(
        val last100Bets: List<DiceGameResultModel>,
    ) : DiceGameBetHistoryFragmentState()

    data class Error(val errorMessage: String) : DiceGameBetHistoryFragmentState()
}

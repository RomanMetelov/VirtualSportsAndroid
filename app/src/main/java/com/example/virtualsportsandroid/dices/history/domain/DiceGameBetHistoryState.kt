package com.example.virtualsportsandroid.dices.history.domain

sealed class DiceGameBetHistoryState {
    object Loading : DiceGameBetHistoryState()
    data class Error(val errorMessage: String) : DiceGameBetHistoryState()
    data class Success(val diceGameBetHistory: List<DiceGameBetHistoryState>) :
        DiceGameBetHistoryState()
}

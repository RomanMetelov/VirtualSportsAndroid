package com.example.virtualsportsandroid.dices.history

import com.example.virtualsportsandroid.dices.DiceGameBetHistoryModel
import com.example.virtualsportsandroid.dices.DiceGameResultModel
import com.example.virtualsportsandroid.filter.data.models.CategoryResponse
import com.example.virtualsportsandroid.filter.data.models.ProviderResponse
import com.example.virtualsportsandroid.filter.ui.FilterFragmentState

sealed class DiceGameBetHistoryFragmentState {
    object Loading : DiceGameBetHistoryFragmentState()
    data class Content(
        val last100Bets: List<DiceGameResultModel>,
    ) : DiceGameBetHistoryFragmentState()

    data class Error(val errorMessage: String) : DiceGameBetHistoryFragmentState()
}

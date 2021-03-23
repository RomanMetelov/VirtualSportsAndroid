package com.example.virtualsportsandroid.mainScreen.ui.model

import com.example.virtualsportsandroid.mainScreen.domain.model.GameModel


sealed class MainFragmentState {
    object Loading : MainFragmentState()
    data class Error(val errorMessage: String) : MainFragmentState()
    data class NotFiltered(val gamesWithFirstTag: List<GameModel>, val allGamesWithoutFirstTag: List<GameModel>) :
        MainFragmentState()

    data class FilteredByCategory(
        val categoryName: String,
        val filteredGames: List<GameModel>
    ) : MainFragmentState()

    data class FilteredByProviders(val filteredGames: List<GameModel>) : MainFragmentState()
    data class FilteredByProvidersAndCategory(val filteredGames: List<GameModel>) :
        MainFragmentState()
}

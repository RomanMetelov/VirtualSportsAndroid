package com.example.virtualsportsandroid.games.ui

import com.example.virtualsportsandroid.games.domain.model.GameModel
import com.example.virtualsportsandroid.games.domain.model.GamesList


sealed class GamesFragmentState {

    object Loading : GamesFragmentState()
    object Content : GamesFragmentState()
    object Error : GamesFragmentState()
    data class NotFiltered(
        val gamesWithFirstTag: GamesList,
        val allGamesWithoutFirstTag: List<GamesList>
    ) :
        GamesFragmentState()

    data class FilteredByCategory(
        val categoryName: String,
        val filteredGames: List<GameModel>
    ) : GamesFragmentState()

    data class FilteredByProviders(val filteredGames: List<GameModel>) : GamesFragmentState()
    data class FilteredByProvidersAndCategory(val filteredGames: List<GameModel>) :
        GamesFragmentState()
}

package com.example.virtualsportsandroid.games.ui

import com.example.virtualsportsandroid.games.domain.model.GameModel
import com.example.virtualsportsandroid.games.domain.model.TagModel


sealed class GamesFragmentState {

    object Loading : GamesFragmentState()
    data class Error(val errorMessage: String) : GamesFragmentState()
    data class NotFiltered(
        val gamesWithFirstTag: TagModel,
        val allGamesWithoutFirstTag: List<TagModel>
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

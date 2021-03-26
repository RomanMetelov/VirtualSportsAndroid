package com.example.virtualsportsandroid.game.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.game.data.ScreenGameModel
import com.example.virtualsportsandroid.game.data.api.GameScreenErrorType
import com.example.virtualsportsandroid.game.domain.AddGameToFavoriteUseCase
import com.example.virtualsportsandroid.game.domain.DelGameFromFavoriteUseCase
import com.example.virtualsportsandroid.game.domain.NetworkToGameScreenErrorMapper
import com.example.virtualsportsandroid.game.domain.PlayGameUseCase
import com.example.virtualsportsandroid.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private typealias ChangeResult = Result<Boolean, GameScreenErrorType>

class GameFragmentViewModel @Inject constructor(
    private val addGameToFavoriteUseCase: AddGameToFavoriteUseCase,
    private val delGameToFavoriteUseCase: DelGameFromFavoriteUseCase,
    private val playGameUseCase: PlayGameUseCase,
    private val networkErrorMapper: NetworkToGameScreenErrorMapper
) : ViewModel() {

    private val _changeGameFavoriteResultLiveData =
        MutableLiveData<ChangeResult>()
    val changeGameFavoriteResultLiveData: LiveData<ChangeResult> =
        _changeGameFavoriteResultLiveData

    private val _playGameResultViewModel =
        MutableLiveData<ChangeResult>()
    val playGameResultViewModel: LiveData<ChangeResult> =
        _playGameResultViewModel

    fun changeGameFavorite(game: ScreenGameModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = when (game.isFavorite) {
                true -> delGameToFavoriteUseCase(gameId = game.id)
                false -> addGameToFavoriteUseCase(gameId = game.id)
            }.mapError {
                networkErrorMapper(it)
            }
            withContext(Dispatchers.Main) {
                _changeGameFavoriteResultLiveData.value = result
            }
        }
    }

    fun playGame(game: ScreenGameModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = playGameUseCase(game.id).mapError {
                networkErrorMapper(it)
            }
            withContext(Dispatchers.Main) {
                _playGameResultViewModel.value = result
            }
        }
    }
}

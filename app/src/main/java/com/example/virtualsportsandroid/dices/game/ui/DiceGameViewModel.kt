package com.example.virtualsportsandroid.dices.game.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.game.data.ScreenGameModel
import com.example.virtualsportsandroid.game.data.api.GameScreenErrorType
import com.example.virtualsportsandroid.game.domain.AddGameToFavoriteUseCase
import com.example.virtualsportsandroid.game.domain.DelGameFromFavoriteUseCase
import com.example.virtualsportsandroid.game.domain.NetworkToGameScreenErrorMapper
import com.example.virtualsportsandroid.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private typealias ChangeResult = Result<Boolean, GameScreenErrorType>

class DiceGameViewModel @Inject constructor(
    private val diceGameResultLoadingUseCase: DiceGameResultLoadingUseCase,
    private val addGameToFavoriteUseCase: AddGameToFavoriteUseCase,
    private val delGameToFavoriteUseCase: DelGameFromFavoriteUseCase,
    private val networkErrorMapper: NetworkToGameScreenErrorMapper
) : ViewModel() {

    private val _changeGameFavoriteResultLiveData =
        MutableLiveData<ChangeResult>()
    val changeGameFavoriteResultLiveData: LiveData<ChangeResult> =
        _changeGameFavoriteResultLiveData

    private val _diceGameResultFragmentStateLiveData =
        MutableLiveData<DiceGameResultFragmentState>()
    val diceGameResultFragmentStateLiveData: LiveData<DiceGameResultFragmentState> =
        _diceGameResultFragmentStateLiveData

    fun placeBet(betTypeId: Int, datetime: String) {
        viewModelScope.launch {
            _diceGameResultFragmentStateLiveData.value = DiceGameResultFragmentState.Loading
            _diceGameResultFragmentStateLiveData.value =
                diceGameResultLoadingUseCase.invoke(betTypeId, datetime)
        }
    }

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
}

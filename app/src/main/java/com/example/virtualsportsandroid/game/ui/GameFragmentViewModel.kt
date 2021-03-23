package com.example.virtualsportsandroid.game.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.game.data.ScreenGameModel
import com.example.virtualsportsandroid.game.data.api.GameScreenErrorType
import com.example.virtualsportsandroid.game.data.api.GameScreenUtils
import com.example.virtualsportsandroid.game.domain.NetworkToGameScreenErrorMapper
import com.example.virtualsportsandroid.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private typealias ChangeResult = Result<Boolean, GameScreenErrorType>

class GameFragmentViewModel @Inject constructor(
    private val gameScreenUtils: GameScreenUtils,
    private val networkErrorMapper: NetworkToGameScreenErrorMapper
) : ViewModel() {

    private val _changeGameFavoriteResultLiveData =
        MutableLiveData<ChangeResult>()
    val changeGameFavoriteResultLiveData: LiveData<ChangeResult> =
        _changeGameFavoriteResultLiveData

    fun changeGameFavorite(game: ScreenGameModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = when (game.isFavorite) {
                true -> gameScreenUtils.delGameFromFavorite(gameId = game.id)
                false -> gameScreenUtils.addGameToFavorite(gameId = game.id)
            }.mapError {
                networkErrorMapper.invoke(it)
            }
            withContext(Dispatchers.Main) {
                _changeGameFavoriteResultLiveData.value = result
            }
        }
    }
}

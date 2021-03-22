package com.example.virtualsportsandroid.game.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.game.data.ScreenGameModel
import com.example.virtualsportsandroid.game.data.api.GameScreenUtils
import com.example.virtualsportsandroid.utils.Result
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameFragmentViewModel @Inject constructor(
    private val gameScreenUtils: GameScreenUtils
) : ViewModel() {

    private val _changeGameFavoriteResultLiveData =
        MutableLiveData<Result<Boolean, NetworkErrorType>>()
    val changeGameFavoriteResultLiveData: LiveData<Result<Boolean, NetworkErrorType>> =
        _changeGameFavoriteResultLiveData

    fun changeGameFavorite(game: ScreenGameModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = when (game.isFavorite) {
                true -> gameScreenUtils.delGameFromFavorite(gameId = game.id)
                false -> gameScreenUtils.addGameToFavorite(gameId = game.id)
            }
            withContext(Dispatchers.Main) {
                _changeGameFavoriteResultLiveData.value = result
            }
        }
    }
}


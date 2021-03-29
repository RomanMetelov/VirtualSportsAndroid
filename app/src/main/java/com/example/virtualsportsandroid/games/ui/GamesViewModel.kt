package com.example.virtualsportsandroid.games.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.game.data.ScreenGameModel
import com.example.virtualsportsandroid.games.data.GamesLoadingError
import com.example.virtualsportsandroid.games.data.GamesRepository
import com.example.virtualsportsandroid.games.domain.LoadingByCategoryAndProvidersUseCase
import com.example.virtualsportsandroid.games.domain.LoadingByCategoryUseCase
import com.example.virtualsportsandroid.games.domain.LoadingByProvidersUseCase
import com.example.virtualsportsandroid.games.domain.NotFilteredGamesLoadingUseCase
import com.example.virtualsportsandroid.main.data.GamesInfoRepository
import com.example.virtualsportsandroid.utils.Result
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("LongParameterList")
class GamesViewModel @Inject constructor(
    private val gamesInfoRepository: GamesInfoRepository,
    private val notFilteredGamesLoadingUseCase: NotFilteredGamesLoadingUseCase,
    private val loadingByCategoryUseCase: LoadingByCategoryUseCase,
    private val loadingByProvidersUseCase: LoadingByProvidersUseCase,
    private val loadingByCategoryAndProvidersUseCase: LoadingByCategoryAndProvidersUseCase,
    private val gamesRepository: GamesRepository,
    private val sharedPref: SharedPref
) : ViewModel() {

    private val _gamesFragmentStateLiveData = MutableLiveData<GamesFragmentState>()
    val gamesFragmentStateLiveData: LiveData<GamesFragmentState> = _gamesFragmentStateLiveData

    private val _loadGamesResult = MutableLiveData<Result<Unit, NetworkErrorType>>()
    val loadGamesResult: LiveData<Result<Unit, NetworkErrorType>> = _loadGamesResult

    fun loadDataFromServer() {
        viewModelScope.launch {
            _gamesFragmentStateLiveData.value = GamesFragmentState.Loading
            _loadGamesResult.postValue(gamesInfoRepository.loadGames())
            _gamesFragmentStateLiveData.value = GamesFragmentState.Content
        }
    }

    fun loadNotFilteredGames() {
        viewModelScope.launch {
            _gamesFragmentStateLiveData.value = GamesFragmentState.Loading
            _gamesFragmentStateLiveData.value = notFilteredGamesLoadingUseCase.invoke()
        }
    }

    fun loadFilteredByCategory(category: String) {
        viewModelScope.launch {
            _gamesFragmentStateLiveData.value = GamesFragmentState.Loading
            _gamesFragmentStateLiveData.value = loadingByCategoryUseCase.invoke(category)
        }
    }

    fun loadFilteredByProviders(providers: List<String>) {
        viewModelScope.launch {
            _gamesFragmentStateLiveData.value = GamesFragmentState.Loading
            _gamesFragmentStateLiveData.value = loadingByProvidersUseCase.invoke(providers)
        }
    }

    fun loadFilteredByCategoryAndProviders(category: String, providers: List<String>) {
        viewModelScope.launch {
            _gamesFragmentStateLiveData.value = GamesFragmentState.Loading
            _gamesFragmentStateLiveData.value =
                loadingByCategoryAndProvidersUseCase.invoke(category, providers)
        }
    }

    fun isAuthorizeUser(): Boolean {
        return (sharedPref.token.isNotEmpty())
    }

    suspend fun loadScreenGameModel(gameId: String): Result<ScreenGameModel, GamesLoadingError> {
        return gamesRepository.getScreenGameModel(gameId)
    }

    fun showError() {
        viewModelScope.launch {
            _gamesFragmentStateLiveData.value = GamesFragmentState.Error
        }
    }
}

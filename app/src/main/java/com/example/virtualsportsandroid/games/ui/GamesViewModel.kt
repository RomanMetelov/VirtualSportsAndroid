package com.example.virtualsportsandroid.games.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.games.domain.LoadingByCategoryAndProvidersUseCase
import com.example.virtualsportsandroid.games.domain.LoadingByCategoryUseCase
import com.example.virtualsportsandroid.games.domain.LoadingByProvidersUseCase
import com.example.virtualsportsandroid.games.domain.NotFilteredGamesLoadingUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class GamesViewModel @Inject constructor(
    private val notFilteredGamesLoadingUseCase: NotFilteredGamesLoadingUseCase,
    private val loadingByCategoryUseCase: LoadingByCategoryUseCase,
    private val loadingByProvidersUseCase: LoadingByProvidersUseCase,
    private val loadingByCategoryAndProvidersUseCase: LoadingByCategoryAndProvidersUseCase
) : ViewModel() {

    private val _mainFragmentStateLiveData = MutableLiveData<GamesFragmentState>()
    val gamesFragmentStateLiveData: LiveData<GamesFragmentState> = _mainFragmentStateLiveData

    fun loadNotFilteredGames() {
        viewModelScope.launch {
            _mainFragmentStateLiveData.value = GamesFragmentState.Loading
            _mainFragmentStateLiveData.value = notFilteredGamesLoadingUseCase.invoke()
        }
    }

    fun loadFilteredByCategory(category: String) {
        viewModelScope.launch {
            _mainFragmentStateLiveData.value = GamesFragmentState.Loading
            _mainFragmentStateLiveData.value = loadingByCategoryUseCase.invoke(category)
        }
    }

    fun loadFilteredByProviders(providers: List<String>) {
        viewModelScope.launch {
            _mainFragmentStateLiveData.value = GamesFragmentState.Loading
            _mainFragmentStateLiveData.value = loadingByProvidersUseCase.invoke(providers)

        }
    }

    fun loadFilteredByCategoryAndProviders(category: String, providers: List<String>) {
        viewModelScope.launch {
            _mainFragmentStateLiveData.value = GamesFragmentState.Loading
            _mainFragmentStateLiveData.value =
                loadingByCategoryAndProvidersUseCase.invoke(category, providers)

        }
    }
}
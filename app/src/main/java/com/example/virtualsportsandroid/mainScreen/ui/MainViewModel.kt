package com.example.virtualsportsandroid.mainScreen.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.mainScreen.domain.LoadingByCategoryAndProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.LoadingByCategoryUseCase
import com.example.virtualsportsandroid.mainScreen.domain.LoadingByProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.NotFilteredGamesLoadingUseCase
import com.example.virtualsportsandroid.mainScreen.ui.model.MainFragmentState
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val notFilteredGamesLoadingUseCase: NotFilteredGamesLoadingUseCase,
    private val loadingByCategoryUseCase: LoadingByCategoryUseCase,
    private val loadingByProvidersUseCase: LoadingByProvidersUseCase,
    private val loadingByCategoryAndProvidersUseCase: LoadingByCategoryAndProvidersUseCase
) : ViewModel() {

    private val _mainFragmentStateLiveData = MutableLiveData<MainFragmentState>()
    val mainFragmentStateLiveData = _mainFragmentStateLiveData

    fun loadNotFilteredGames() {
        viewModelScope.launch {
            _mainFragmentStateLiveData.value = MainFragmentState.Loading
            notFilteredGamesLoadingUseCase().let {
                _mainFragmentStateLiveData.value = it
            }
        }
    }

    fun loadFilteredByCategory(category: String) {
        viewModelScope.launch {
            _mainFragmentStateLiveData.value = MainFragmentState.Loading
            loadingByCategoryUseCase(category).let {
                _mainFragmentStateLiveData.value = it
            }
        }
    }

    fun loadFilteredByProviders(providers: List<String>) {
        viewModelScope.launch {
            _mainFragmentStateLiveData.value = MainFragmentState.Loading
            loadingByProvidersUseCase(providers).let {
                _mainFragmentStateLiveData.value = it
            }
        }
    }

    fun loadFilteredByCategoryAndProviders(category: String, providers: List<String>) {
        viewModelScope.launch {
            _mainFragmentStateLiveData.value = MainFragmentState.Loading
            loadingByCategoryAndProvidersUseCase(category, providers).let {
                _mainFragmentStateLiveData.value = it
            }
        }
    }
}

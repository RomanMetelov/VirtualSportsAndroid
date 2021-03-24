package com.example.virtualsportsandroid.mainScreen.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.mainScreen.domain.LoadingByCategoryAndProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.LoadingByCategoryUseCase
import com.example.virtualsportsandroid.mainScreen.domain.LoadingByProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.NotFilteredGamesLoadingUseCase
import com.example.virtualsportsandroid.mainScreen.ui.model.MainFragmentState
import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val notFilteredGamesLoadingUseCase: NotFilteredGamesLoadingUseCase,
    private val loadingByCategoryUseCase: LoadingByCategoryUseCase,
    private val loadingByProvidersUseCase: LoadingByProvidersUseCase,
    private val loadingByCategoryAndProvidersUseCase: LoadingByCategoryAndProvidersUseCase,
    private val sharedPref: SharedPref
) : ViewModel() {

    private val _mainFragmentStateLiveData = MutableLiveData<MainFragmentState>()
    val mainFragmentStateLiveData = _mainFragmentStateLiveData

    private val _isAuthorizedLiveData = MutableLiveData<Boolean>()
    val isAuthorizedLiveData: LiveData<Boolean> = _isAuthorizedLiveData

    fun loadNotFilteredGames() {
        viewModelScope.launch {
            _mainFragmentStateLiveData.value = notFilteredGamesLoadingUseCase.invoke()
        }
    }

    fun loadFilteredByCategory(category: String) {
        viewModelScope.launch {
            _mainFragmentStateLiveData.value = loadingByCategoryUseCase.invoke(category)
        }
    }

    fun loadFilteredByProviders(providers: List<String>) {
        viewModelScope.launch {
            _mainFragmentStateLiveData.value = loadingByProvidersUseCase.invoke(providers)

        }
    }

    fun loadFilteredByCategoryAndProviders(category: String, providers: List<String>) {
        viewModelScope.launch {
            _mainFragmentStateLiveData.value =
                loadingByCategoryAndProvidersUseCase.invoke(category, providers)

        }
    }

    fun checkIsAuthorized() {
        viewModelScope.launch(Dispatchers.IO) {
            _isAuthorizedLiveData.postValue(sharedPref.token.isNotEmpty())
        }
    }
}

package com.example.virtualsportsandroid.mainScreen.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.mainScreen.domain.NotFilteredGamesLoadingUseCase
import com.example.virtualsportsandroid.mainScreen.ui.model.MainFragmentState
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val notFilteredGamesLoadingUseCase: NotFilteredGamesLoadingUseCase
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
}
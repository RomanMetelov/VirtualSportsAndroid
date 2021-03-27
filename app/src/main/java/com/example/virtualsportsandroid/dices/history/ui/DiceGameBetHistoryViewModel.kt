package com.example.virtualsportsandroid.dices.history.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.dices.history.domain.DiceGameBetHistoryLoadingUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class DiceGameBetHistoryViewModel @Inject constructor(
    private val diceGameBetHistoryLoadingUseCase: DiceGameBetHistoryLoadingUseCase
) : ViewModel() {

    private val _diceGameBetHistoryFragmentStateLiveData =
        MutableLiveData<DiceGameBetHistoryFragmentState>()
    val diceGameBetHistoryFragmentStateLiveData
            : LiveData<DiceGameBetHistoryFragmentState> = _diceGameBetHistoryFragmentStateLiveData

    fun loadData() {
        viewModelScope.launch {
            _diceGameBetHistoryFragmentStateLiveData.value = DiceGameBetHistoryFragmentState.Loading
            _diceGameBetHistoryFragmentStateLiveData.value =
                diceGameBetHistoryLoadingUseCase.invoke()
        }
    }
}

package com.example.virtualsportsandroid.dices.game.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.dices.DiceGameResultModel
import com.example.virtualsportsandroid.dices.game.DiceGameResultFragmentState
import com.example.virtualsportsandroid.filter.domain.FiltersLoadingUseCase
import com.example.virtualsportsandroid.utils.Result
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import kotlinx.coroutines.launch
import javax.inject.Inject

class DiceGameViewModel @Inject constructor(
    private val diceGameResultLoadingUseCase: DiceGameResultLoadingUseCase
) : ViewModel() {

    private val _diceGameResultFragmentStateLiveData =
        MutableLiveData<DiceGameResultFragmentState>()
    val diceGameResultFragmentStateLiveData: LiveData<DiceGameResultFragmentState> =
        _diceGameResultFragmentStateLiveData

    fun placeBet(betTypeId: Int, datetime: String) {
        viewModelScope.launch {
            _diceGameResultFragmentStateLiveData.value = DiceGameResultFragmentState.Loading
//            val result = diceGameResultLoadingUseCase.invoke(betTypeId, datetime)
//            when (result.isError) {
//                true -> _diceGameResultFragmentStateLiveData.value =
//                    DiceGameResultFragmentState.Error(result.errorResult.toString())
//                false -> _diceGameResultFragmentStateLiveData.value =
//                    DiceGameResultFragmentState.Content(result.successResult)
//            }
        }
    }
}

package com.example.virtualsportsandroid.dices.game.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.dices.game.DiceGameResultFragmentState
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
//            Log.d("refrefreffrefref", "delay")
//            delay(1500)
//            Log.d("refrefreffrefref", "delayover")
            _diceGameResultFragmentStateLiveData.value =
                diceGameResultLoadingUseCase.invoke(betTypeId, datetime)
        }
    }
}

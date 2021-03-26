package com.example.virtualsportsandroid.dices.game.ui

import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.dices.BetType
import com.example.virtualsportsandroid.dices.DiceGameResultModel
import com.example.virtualsportsandroid.dices.game.DiceGameResultFragmentState
import com.example.virtualsportsandroid.filter.domain.FiltersLoadingUseCase
import com.example.virtualsportsandroid.utils.Result
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

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

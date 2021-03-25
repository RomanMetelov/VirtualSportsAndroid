package com.example.virtualsportsandroid.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val sharedPref: SharedPref
) : ViewModel() {

    private val _isAuthorizedLiveData = MutableLiveData<Boolean>()
    val isAuthorizedLiveData: LiveData<Boolean> = _isAuthorizedLiveData

    private val _containerStateLiveData = MutableLiveData<MainFragmentContainerState>()
    val containerStateLiveData: LiveData<MainFragmentContainerState> = _containerStateLiveData

    fun checkIsAuthorized() {
        viewModelScope.launch(Dispatchers.IO) {
            _isAuthorizedLiveData.postValue(sharedPref.token.isNotEmpty())
        }
    }

    fun showFilterFragment() {
        viewModelScope.launch {
            _containerStateLiveData.value = MainFragmentContainerState.Filter
        }
    }

    fun showGamesFragment(category: String?, providers: List<String>?) {
        viewModelScope.launch {
            _containerStateLiveData.value = MainFragmentContainerState.Games(category, providers)
        }
    }
}

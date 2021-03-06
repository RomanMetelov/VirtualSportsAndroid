package com.example.virtualsportsandroid.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.main.data.GamesInfoRepository
import com.example.virtualsportsandroid.utils.Result
import com.example.virtualsportsandroid.utils.api.AuthorizationRepository
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val sharedPref: SharedPref,
    private val authorizationRepository: AuthorizationRepository
) : ViewModel() {

    private val _isAuthorizedLiveData = MutableLiveData<Boolean>()
    val isAuthorizedLiveData: LiveData<Boolean> = _isAuthorizedLiveData

    private val _containerStateLiveData = MutableLiveData<MainFragmentContainerState>()
    val containerStateLiveData: LiveData<MainFragmentContainerState> = _containerStateLiveData

    private val _logoutResultLiveData = MutableLiveData<Result<Unit, NetworkErrorType>>()
    val logoutResultLiveData: LiveData<Result<Unit, NetworkErrorType>> = _logoutResultLiveData

    private val _mainFragmentStateLiveData = MutableLiveData<MainFragmentState>()
    val mainFragmentStateLiveData: LiveData<MainFragmentState> = _mainFragmentStateLiveData

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

    fun showError() {
        viewModelScope.launch {
            _mainFragmentStateLiveData.value = MainFragmentState.Error
        }
    }

    fun showGamesFragment(category: String?, providers: List<String>?) {
        viewModelScope.launch {
            _containerStateLiveData.value = MainFragmentContainerState.Games(category, providers)
        }
    }

    fun logout(updateCallback: () -> Unit) {
        viewModelScope.launch {
            authorizationRepository.logout()
            sharedPref.token = ""
            _logoutResultLiveData.value = Result.success(Unit)
            updateCallback()
        }
    }

    fun loadConfigs() {
        viewModelScope.launch {
            _mainFragmentStateLiveData.value = MainFragmentState.Loading
            _mainFragmentStateLiveData.value = MainFragmentState.Content
            showGamesFragment(null, null)
        }
    }
}

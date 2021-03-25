package com.example.virtualsportsandroid.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.main.data.ConfigsRepository
import com.example.virtualsportsandroid.utils.Result
import com.example.virtualsportsandroid.utils.api.AuthorizationRepository
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val sharedPref: SharedPref,
    private val authorizationRepository: AuthorizationRepository,
    private val configsRepository: ConfigsRepository
) : ViewModel() {

    private val _isAuthorizedLiveData = MutableLiveData<Boolean>()
    val isAuthorizedLiveData: LiveData<Boolean> = _isAuthorizedLiveData

    private val _containerStateLiveData = MutableLiveData<MainFragmentContainerState>()
    val containerStateLiveData: LiveData<MainFragmentContainerState> = _containerStateLiveData

    private val _logoutResultLiveData = MutableLiveData<Result<Boolean, NetworkErrorType>>()
    val logoutResultLiveData: LiveData<Result<Boolean, NetworkErrorType>> = _logoutResultLiveData

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

    fun showGamesFragment(category: String?, providers: List<String>?) {
        viewModelScope.launch {
            _containerStateLiveData.value = MainFragmentContainerState.Games(category, providers)
        }
    }

    fun logout() {
        viewModelScope.launch {
            authorizationRepository.logout()
            sharedPref.token = ""
            _logoutResultLiveData.value = Result.success(true)
            _isAuthorizedLiveData.value = false
        }
    }

    fun loadConfigs() {
        viewModelScope.launch {
            _mainFragmentStateLiveData.value = MainFragmentState.LOADING
            configsRepository.loadConfigs()
            _mainFragmentStateLiveData.value = MainFragmentState.CONTENT
        }
    }
}

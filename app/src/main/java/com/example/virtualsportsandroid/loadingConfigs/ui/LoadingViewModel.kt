package com.example.virtualsportsandroid.loadingConfigs.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.loadingConfigs.data.ConfigsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoadingViewModel @Inject constructor(private val configsRepository: ConfigsRepository) :
    ViewModel() {

    private val _loadingStateLiveData = MutableLiveData<ConfigsLoadingState>()
    val loadingStateLiveData: LiveData<ConfigsLoadingState> = _loadingStateLiveData

    fun loadConfigs() {
        viewModelScope.launch {
            _loadingStateLiveData.value = ConfigsLoadingState.Loading
            configsRepository.loadConfigs()
            _loadingStateLiveData.value = ConfigsLoadingState.Successful
        }
    }
}

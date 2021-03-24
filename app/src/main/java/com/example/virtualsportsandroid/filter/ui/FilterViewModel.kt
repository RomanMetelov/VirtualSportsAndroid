package com.example.virtualsportsandroid.filter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.filter.domain.FiltersLoadingUseCase
import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilterViewModel @Inject constructor(
    private val filtersLoadingUseCase: FiltersLoadingUseCase,
    private val sharedPref: SharedPref
) : ViewModel() {

    private val _filterFragmentStateLiveData = MutableLiveData<FilterFragmentState>()
    val filterFragmentStateLiveData: LiveData<FilterFragmentState> = _filterFragmentStateLiveData

    private val _selectedCategoryLiveData = MutableLiveData<String?>()
    val selectedCategoryLiveData: LiveData<String?> = _selectedCategoryLiveData

    private val _selectedProvidersLiveData = MutableLiveData<List<String>>().apply {
        value = emptyList()
    }
    val selectedProvidersLiveData: LiveData<List<String>> = _selectedProvidersLiveData

    private val _isAuthorizedLiveData = MutableLiveData<Boolean>()
    val isAuthorizedLiveData: LiveData<Boolean> = _isAuthorizedLiveData

    fun loadData() {
        viewModelScope.launch {
            _filterFragmentStateLiveData.value = filtersLoadingUseCase.invoke()
        }
    }

    fun selectCategory(category: String) {
        viewModelScope.launch {
            _selectedCategoryLiveData.value = category
        }
    }

    fun unselectCategory() {
        viewModelScope.launch {
            _selectedCategoryLiveData.value = null
        }
    }

    fun selectProvider(provider: String) {
        viewModelScope.launch {
            (_selectedProvidersLiveData.value ?: emptyList()).let {
                _selectedProvidersLiveData.value = it + provider
            }
        }
    }

    fun unselectProvider(provider: String) {
        viewModelScope.launch {
            (_selectedProvidersLiveData.value ?: emptyList()).let { selectedProviders ->
                _selectedProvidersLiveData.value = selectedProviders.filter { it != provider }
            }
        }
    }

    fun checkIsAuthorized() {
        viewModelScope.launch(Dispatchers.IO) {
            _isAuthorizedLiveData.postValue(sharedPref.token.isNotEmpty())
        }
    }
}

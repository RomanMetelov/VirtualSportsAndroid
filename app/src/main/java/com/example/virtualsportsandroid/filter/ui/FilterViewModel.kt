package com.example.virtualsportsandroid.filter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.filter.domain.FiltersLoadingUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilterViewModel @Inject constructor(
    private val filtersLoadingUseCase: FiltersLoadingUseCase
) : ViewModel() {

    private val _filterFragmentStateLiveData = MutableLiveData<FilterFragmentState>()
    val filterFragmentStateLiveData: LiveData<FilterFragmentState> = _filterFragmentStateLiveData

    private val _selectedCategoryLiveData = MutableLiveData<String?>()
    val selectedCategoryLiveData: LiveData<String?> = _selectedCategoryLiveData

    fun loadData() {
        viewModelScope.launch {
            _filterFragmentStateLiveData.value = FilterFragmentState.Loading
            _filterFragmentStateLiveData.value = filtersLoadingUseCase.invoke()
        }
    }

    fun selectCategory(category: String) {
        viewModelScope.launch {
            _selectedCategoryLiveData.value = category
        }
    }
}
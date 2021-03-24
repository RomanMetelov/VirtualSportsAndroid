package com.example.virtualsportsandroid.filter.ui

import com.example.virtualsportsandroid.loadingConfigs.data.CategoryResponse
import com.example.virtualsportsandroid.loadingConfigs.data.ProviderResponse

sealed class FilterFragmentState {
    data class Content(
        val categories: List<CategoryResponse>,
        val providers: List<ProviderResponse>
    ) : FilterFragmentState()

    data class Error(val errorMessage: String) : FilterFragmentState()
}

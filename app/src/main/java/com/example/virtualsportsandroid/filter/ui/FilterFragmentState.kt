package com.example.virtualsportsandroid.filter.ui

import com.example.virtualsportsandroid.filter.data.models.CategoryResponse
import com.example.virtualsportsandroid.filter.data.models.ProviderResponse

sealed class FilterFragmentState {
    object Loading : FilterFragmentState()
    data class Content(
        val categories: List<CategoryResponse>,
        val providers: List<ProviderResponse>
    ) : FilterFragmentState()

    data class Error(val errorMessage: String) : FilterFragmentState()
}

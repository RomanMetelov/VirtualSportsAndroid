package com.example.virtualsportsandroid.filter.ui

import com.example.virtualsportsandroid.main.data.CategoryResponse
import com.example.virtualsportsandroid.main.data.ProviderResponse

sealed class FilterFragmentState {
    object Loading : FilterFragmentState()
    data class Content(
        val categories: List<CategoryResponse>,
        val providers: List<ProviderResponse>
    ) : FilterFragmentState()

    object Error : FilterFragmentState()
}

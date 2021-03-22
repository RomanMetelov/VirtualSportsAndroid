package com.example.virtualsportsandroid.filter.domain

import com.example.virtualsportsandroid.filter.data.FiltersRepository
import com.example.virtualsportsandroid.filter.ui.FilterFragmentState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FiltersLoadingUseCase @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val filtersRepository: FiltersRepository
) {
    suspend operator fun invoke(): FilterFragmentState = withContext(dispatcher) {
        val categoriesResult = filtersRepository.getCategories()
        val providersResult = filtersRepository.getProviders()
        when {
            categoriesResult.isError -> FilterFragmentState.Error(categoriesResult.errorResult)
            providersResult.isError -> FilterFragmentState.Error(categoriesResult.errorResult)
            else -> FilterFragmentState.Content(
                categoriesResult.successResult,
                providersResult.successResult
            )
        }
    }
}

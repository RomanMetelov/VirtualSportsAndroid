package com.example.virtualsportsandroid.filter.data

import com.example.virtualsportsandroid.filter.data.models.CategoryResponse
import com.example.virtualsportsandroid.filter.data.models.ProviderResponse
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import com.example.virtualsportsandroid.utils.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Suppress("MagicNumber")
class FiltersRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getCategories(): Result<List<CategoryResponse>, String> = withContext(dispatcher) {
        delay(1000)
        Result.success(
            listOf(
                CategoryResponse(
                    "favorites",
                    "Favorites",
                    ""
                ),
                CategoryResponse(
                    "category1",
                    "Category 1",
                    "https://cdn.iconscout.com/icon/premium/png-512-thumb/ios-application-placeholder-2-601407.png"
                ),
                CategoryResponse(
                    "category2",
                    "Category 2",
                    "https://cdn.iconscout.com/icon/premium/png-512-thumb/ios-application-placeholder-2-601407.png"
                ),
                CategoryResponse(
                    "category3",
                    "Category 3",
                    "https://cdn.iconscout.com/icon/premium/png-512-thumb/ios-application-placeholder-2-601407.png"
                ),
                CategoryResponse(
                    "category4",
                    "Category 4",
                    "https://cdn.iconscout.com/icon/premium/png-512-thumb/ios-application-placeholder-2-601407.png"
                ),

                CategoryResponse(
                    "category5",
                    "Category 5",
                    "https://cdn.iconscout.com/icon/premium/png-512-thumb/ios-application-placeholder-2-601407.png"
                )
            )
        )
    }

    suspend fun getProviders(): Result<List<ProviderResponse>, String> = withContext(dispatcher) {
        delay(1000)
        Result.success(
            listOf(
                ProviderResponse(
                    "provider1",
                    "https://t3.ftcdn.net/jpg/02/48/42/64/360_F_248426448_NVKLywWqArG2ADUxDq6QprtIzsF82dMF.jpg"
                ),
                ProviderResponse(
                    "provider2",
                    "https://t3.ftcdn.net/jpg/02/48/42/64/360_F_248426448_NVKLywWqArG2ADUxDq6QprtIzsF82dMF.jpg"
                ),
                ProviderResponse(
                    "provider3",
                    "https://t3.ftcdn.net/jpg/02/48/42/64/360_F_248426448_NVKLywWqArG2ADUxDq6QprtIzsF82dMF.jpg"
                ),
                ProviderResponse(
                    "provider4",
                    "https://t3.ftcdn.net/jpg/02/48/42/64/360_F_248426448_NVKLywWqArG2ADUxDq6QprtIzsF82dMF.jpg"
                ),
                ProviderResponse(
                    "provider5",
                    "https://t3.ftcdn.net/jpg/02/48/42/64/360_F_248426448_NVKLywWqArG2ADUxDq6QprtIzsF82dMF.jpg"
                ),
                ProviderResponse(
                    "provider6",
                    "https://t3.ftcdn.net/jpg/02/48/42/64/360_F_248426448_NVKLywWqArG2ADUxDq6QprtIzsF82dMF.jpg"
                ),
                ProviderResponse(
                    "provider7",
                    "https://t3.ftcdn.net/jpg/02/48/42/64/360_F_248426448_NVKLywWqArG2ADUxDq6QprtIzsF82dMF.jpg"
                )
            )
        )
    }
}

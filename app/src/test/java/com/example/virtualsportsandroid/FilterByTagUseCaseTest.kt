package com.example.virtualsportsandroid

import com.example.virtualsportsandroid.loadingConfigs.data.*
import com.example.virtualsportsandroid.mainScreen.domain.FilterByTagUseCase
import com.example.virtualsportsandroid.mainScreen.domain.model.GameModel
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

internal class FilterByTagUseCaseTest {
    @ExperimentalCoroutinesApi
    @Test
    fun `filter by tag works correctly`() {
        val fakeConfigsResponse = ConfigsResponse(
            listOf(
                GameResponse(
                    "id1", "", "provider1", listOf(
                        "category1", "category2"
                    ),
                    listOf("all"),
                    "gameURL",
                    "imageURL"
                ),
                GameResponse(
                    "id2", "", "provider2", listOf(
                        "category1"
                    ),
                    listOf("all"),
                    "gameURL",
                    "imageURL"
                ),
                GameResponse(
                    "id3", "", "provider2", listOf(
                        "category1", "category2", "category3"
                    ),
                    listOf("all"),
                    "gameURL",
                    "imageURL"
                ),
                GameResponse(
                    "id4", "", "provider3", listOf(
                        "category1"
                    ),
                    listOf("top"),
                    "gameURL",
                    "imageURL"
                ),
                GameResponse(
                    "id5", "", "provider3", listOf(
                        "category2"
                    ),
                    listOf("top", "all"),
                    "gameURL",
                    "imageURL"
                ),
                GameResponse(
                    "id6", "", "provider4", listOf(
                        "category2"
                    ),
                    listOf("top"),
                    "gameURL",
                    "imageURL"
                )
            ),
            listOf(
                CategoryResponse("category1", "imageURL", "Category 1"),
                CategoryResponse("category2", "imageURL", "Category 2"),
                CategoryResponse("category3", "imageURL", "Category 3"),
            ),
            listOf(
                ProviderResponse("provider1", "imageURL", "Provider 1"),
                ProviderResponse("provider2", "imageURL", "Provider 2"),
                ProviderResponse("provider3", "imageURL", "Provider 3"),
                ProviderResponse("provider4", "imageURL", "Provider 4")
            ),
            listOf(
                TagResponse("top", "Top"),
                TagResponse("all", "All games")
            )
        )
        val expectedResult = listOf(
            GameModel("id1", "", "imageURL"),
            GameModel("id2", "", "imageURL"),
            GameModel("id3", "", "imageURL"),
            GameModel("id5", "", "imageURL")
        )
        runBlockingTest {
            FilterByTagUseCase(TestCoroutineDispatcher()).invoke(
                "all",
                fakeConfigsResponse
            ) shouldBe expectedResult
        }
    }
}
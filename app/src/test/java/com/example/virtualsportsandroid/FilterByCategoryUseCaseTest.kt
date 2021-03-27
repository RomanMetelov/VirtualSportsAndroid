package com.example.virtualsportsandroid

import com.example.virtualsportsandroid.games.domain.FilterByCategoryUseCase
import com.example.virtualsportsandroid.games.domain.model.GameModel
import com.example.virtualsportsandroid.main.data.*
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test


internal class FilterByCategoryUseCaseTest {
    @ExperimentalCoroutinesApi
    @Test
    fun `filter by category works correctly`() {
        val fakeConfigsResponse = GamesResponse(
            listOf(
                GameResponse(
                    "id1", "", "provider1", listOf(
                        "category1", "category2"
                    ),
                    listOf("all"),
                    "gameURL"
                ),
                GameResponse(
                    "id2", "", "provider2", listOf(
                        "category1"
                    ),
                    listOf("all"),
                    "gameURL"
                ),
                GameResponse(
                    "id3", "", "provider2", listOf(
                        "category1", "category2", "category3"
                    ),
                    listOf("all"),
                    "gameURL"
                ),
                GameResponse(
                    "id4", "", "provider3", listOf(
                        "category1"
                    ),
                    listOf("top"),
                    "gameURL"
                ),
                GameResponse(
                    "id5", "", "provider3", listOf(
                        "category2"
                    ),
                    listOf("top", "all"),
                    "gameURL"
                ),
                GameResponse(
                    "id6", "", "provider4", listOf(
                        "category2"
                    ),
                    listOf("top"),
                    "gameURL"
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
            GameModel("id1", "", ""),
            GameModel("id2", "", ""),
            GameModel("id3", "", ""),
            GameModel("id4", "", "")
        )
        runBlockingTest {
            FilterByCategoryUseCase(TestCoroutineDispatcher()).invoke(
                "category1",
                fakeConfigsResponse
            ) shouldBe expectedResult
        }
    }
}
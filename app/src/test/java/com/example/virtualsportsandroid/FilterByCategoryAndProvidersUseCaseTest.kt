package com.example.virtualsportsandroid

import com.example.virtualsportsandroid.mainScreen.data.model.GameResponse
import com.example.virtualsportsandroid.mainScreen.data.model.GamesResponse
import com.example.virtualsportsandroid.mainScreen.domain.FilterByCategoryAndProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.model.GameModel
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

internal class FilterByCategoryAndProvidersUseCaseTest {
    @ExperimentalCoroutinesApi
    @Test
    fun `filter by category and providers works correctly`() {
        val fakeGamesResponse = GamesResponse(
            listOf(
                GameResponse(
                    "id1", "", "", "provider1", listOf(
                        "category1", "category2"
                    ),
                    listOf("all")
                ),
                GameResponse(
                    "id2", "", "", "provider2", listOf(
                        "category1"
                    ),
                    listOf("all")
                ),
                GameResponse(
                    "id3", "", "", "provider2", listOf(
                        "category1", "category2", "category3"
                    ),
                    listOf("all")
                ),
                GameResponse(
                    "id4", "", "", "provider3", listOf(
                        "category1"
                    ),
                    listOf("all")
                ),
                GameResponse(
                    "id5", "", "", "provider3", listOf(
                        "category1", "category2"
                    ),
                    listOf("all")
                ),
                GameResponse(
                    "id6", "", "", "provider4", listOf(
                        "category1", "category2"
                    ),
                    listOf("all")
                )
            )
        )
        val expectedResult = listOf(
            GameModel("id2", "", ""),
            GameModel("id3", "", ""),
            GameModel("id4", "", ""),
            GameModel("id5", "", "")
        )
        runBlockingTest {
            FilterByCategoryAndProvidersUseCase(TestCoroutineDispatcher()).invoke(
                "category1",
                listOf("provider2", "provider3"),
                fakeGamesResponse
            ) shouldBe expectedResult
        }
    }
}

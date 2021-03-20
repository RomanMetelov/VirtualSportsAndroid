package com.example.virtualsportsandroid.mainScreen.domain

import com.example.virtualsportsandroid.mainScreen.data.model.GameResponse
import com.example.virtualsportsandroid.mainScreen.data.model.GamesResponse
import com.example.virtualsportsandroid.mainScreen.domain.model.GameModel
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

internal class FilterByProvidersUseCaseTest {
    @ExperimentalCoroutinesApi
    @Test
    fun `filter by providers works correctly`() {
        val fakeGamesResponse = GamesResponse(
            listOf(
                GameResponse(
                    "id1", "", "", "provider1", listOf(
                        "category1", "category2"
                    )
                ),
                GameResponse(
                    "id2", "", "", "provider2", listOf(
                        "category1"
                    )
                ),
                GameResponse(
                    "id3", "", "", "provider2", listOf(
                        "category1", "category2", "category3"
                    )
                ),
                GameResponse(
                    "id4", "", "", "provider3", listOf(
                        "category1"
                    )
                ),
                GameResponse(
                    "id5", "", "", "provider3", listOf(
                        "category2"
                    )
                ),
                GameResponse(
                    "id6", "", "", "provider4", listOf(
                        "category2"
                    )
                )
            )
        )
        val expectedResult = listOf(
            GameModel("id1", "", ""),
            GameModel("id2", "", ""),
            GameModel("id3", "", "")
        )
        runBlockingTest {
            FilterByProvidersUseCase(TestCoroutineDispatcher()).invoke(
                listOf("provider1", "provider2"),
                fakeGamesResponse
            ) shouldBe expectedResult
        }
    }
}
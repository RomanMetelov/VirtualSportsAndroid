package com.example.virtualsportsandroid

import com.example.virtualsportsandroid.mainScreen.data.model.GameResponse
import com.example.virtualsportsandroid.mainScreen.data.model.GamesResponse
import com.example.virtualsportsandroid.mainScreen.domain.FilterByTagUseCase
import com.example.virtualsportsandroid.mainScreen.domain.model.GameModel
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

internal class FilterByTagUseCaseTest {
    @ExperimentalCoroutinesApi
    @Test
    fun `filter by tag works correctly`() {
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
                    listOf("top")
                ),
                GameResponse(
                    "id5", "", "", "provider3", listOf(
                        "category2"
                    ),
                    listOf("top", "all")
                ),
                GameResponse(
                    "id6", "", "", "provider4", listOf(
                        "category2"
                    ),
                    listOf("top")
                )
            )
        )
        val expectedResult = listOf(
            GameModel("id1", "", ""),
            GameModel("id2", "", ""),
            GameModel("id3", "", ""),
            GameModel("id5", "", "")
        )
        runBlockingTest {
            FilterByTagUseCase(TestCoroutineDispatcher()).invoke(
                "all",
                fakeGamesResponse
            ) shouldBe expectedResult
        }
    }
}
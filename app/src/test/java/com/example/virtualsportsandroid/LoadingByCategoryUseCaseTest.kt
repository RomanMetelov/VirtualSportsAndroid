package com.example.virtualsportsandroid

import com.example.virtualsportsandroid.games.data.GamesRepository
import com.example.virtualsportsandroid.games.data.GamesLoadingError
import com.example.virtualsportsandroid.games.domain.LoadingByCategoryUseCase
import com.example.virtualsportsandroid.games.domain.model.GameModel
import com.example.virtualsportsandroid.games.ui.GamesFragmentState
import com.example.virtualsportsandroid.utils.Result
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class LoadingByCategoryUseCaseTest {
    @Test
    fun `loading by category use case without error works correctly`() {
        val fakeFilteredGames = listOf(
            GameModel("id1", "name1", "url1"),
            GameModel("id2", "name2", "url2"),
            GameModel("id3", "name3", "url3")
        )
        val fakeCategory = "category"
        val mockGamesRepository = mockk<GamesRepository> {
            coEvery { getGamesFilteredByCategory(any()) } returns Result.success(fakeFilteredGames)
            coEvery { getCategoryName(any()) } returns Result.success(fakeCategory)
        }
        runBlockingTest {
            LoadingByCategoryUseCase(
                TestCoroutineDispatcher(),
                mockGamesRepository
            ).invoke(fakeCategory) shouldBe GamesFragmentState.FilteredByCategory(
                fakeCategory,
                fakeFilteredGames
            )
        }
    }

    @Test
    fun `loading by category use case with not found error works correctly`() {
        val mockGamesRepository = mockk<GamesRepository> {
            coEvery { getGamesFilteredByCategory(any()) } returns Result.error(GamesLoadingError.NOT_FOUND)
        }
        val fakeCategory = "category"
        runBlockingTest {
            LoadingByCategoryUseCase(
                TestCoroutineDispatcher(),
                mockGamesRepository
            ).invoke(fakeCategory) shouldBe GamesFragmentState.Error
        }
    }

    @Test
    fun `loading by category use case with empty list works correctly`() {
        val mockGamesRepository = mockk<GamesRepository> {
            coEvery { getGamesFilteredByCategory(any()) } returns Result.success(emptyList())
        }
        val fakeCategory = "category"
        runBlockingTest {
            LoadingByCategoryUseCase(
                TestCoroutineDispatcher(),
                mockGamesRepository
            ).invoke(fakeCategory) shouldBe GamesFragmentState.Error
        }
    }
}
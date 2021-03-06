package com.example.virtualsportsandroid

import com.example.virtualsportsandroid.games.data.GamesRepository
import com.example.virtualsportsandroid.games.data.GamesLoadingError
import com.example.virtualsportsandroid.games.domain.LoadingByCategoryAndProvidersUseCase
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
internal class LoadingByCategoryAndProvidersUseCaseTest {

    @Test
    fun `loading by category and providers without error works correctly`() {
        val fakeFilteredGames = listOf(
            GameModel("id1", "name1", "url1"),
            GameModel("id2", "name2", "url2"),
            GameModel("id3", "name3", "url3")
        )
        val mockGamesRepository = mockk<GamesRepository> {
            coEvery { getGamesFilteredByCategoryAndProviders(any(), any()) } returns Result.success(
                fakeFilteredGames
            )
        }
        val fakeProviders = listOf("provider1", "provider2", "provider3")
        val fakeCategory = "category"
        runBlockingTest {
            LoadingByCategoryAndProvidersUseCase(
                TestCoroutineDispatcher(),
                mockGamesRepository
            ).invoke(
                fakeCategory,
                fakeProviders
            ) shouldBe GamesFragmentState.FilteredByProvidersAndCategory(fakeFilteredGames)
        }
    }

    @Test
    fun `loading by category and providers with not found error works correctly`() {
        val mockGamesRepository = mockk<GamesRepository> {
            coEvery { getGamesFilteredByCategoryAndProviders(any(), any()) } returns Result.error(
                GamesLoadingError.NOT_FOUND
            )
        }
        val fakeProviders = listOf("provider1", "provider2", "provider3")
        val fakeCategory = "category"
        runBlockingTest {
            LoadingByCategoryAndProvidersUseCase(
                TestCoroutineDispatcher(),
                mockGamesRepository
            ).invoke(fakeCategory, fakeProviders) shouldBe GamesFragmentState.Error
        }
    }

    @Test
    fun `loading by category and providers with empty list works correctly`() {
        val mockGamesRepository = mockk<GamesRepository> {
            coEvery { getGamesFilteredByCategoryAndProviders(any(), any()) } returns Result.success(
                emptyList()
            )
        }
        val fakeProviders = listOf("provider1", "provider2", "provider3")
        val fakeCategory = "category"
        runBlockingTest {
            LoadingByCategoryAndProvidersUseCase(
                TestCoroutineDispatcher(),
                mockGamesRepository
            ).invoke(fakeCategory, fakeProviders) shouldBe GamesFragmentState.Error
        }
    }
}
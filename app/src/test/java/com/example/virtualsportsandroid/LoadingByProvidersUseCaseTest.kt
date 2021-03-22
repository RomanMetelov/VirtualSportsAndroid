package com.example.virtualsportsandroid

import android.content.Context
import com.example.virtualsportsandroid.mainScreen.data.GamesRepository
import com.example.virtualsportsandroid.mainScreen.data.model.GamesLoadingError
import com.example.virtualsportsandroid.mainScreen.domain.LoadingByProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.model.GameModel
import com.example.virtualsportsandroid.mainScreen.ui.model.MainFragmentState
import com.example.virtualsportsandroid.utils.Result
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class LoadingByProvidersUseCaseTest {

    @Test
    fun `loading by providers without error works correctly`() {
        val mockContext = mockk<Context> {
            every { getString(any()) } returns "Error"
        }
        val fakeFilteredGames = listOf(
            GameModel("id1", "name1", "url1"),
            GameModel("id2", "name2", "url2"),
            GameModel("id3", "name3", "url3")
        )
        val mockGamesRepository = mockk<GamesRepository> {
            coEvery { getGamesFilteredByProviders(any()) } returns Result.success(fakeFilteredGames)
        }
        val fakeProviders = listOf("provider1", "provider2", "provider3")
        runBlockingTest {
            LoadingByProvidersUseCase(
                TestCoroutineDispatcher(),
                mockGamesRepository,
                mockContext
            ).invoke(fakeProviders) shouldBe MainFragmentState.FilteredByProviders(fakeFilteredGames)
        }
    }

    @Test
    fun `loading by providers with not found error works correctly`() {
        val errorMessage = "Error"
        val mockContext = mockk<Context> {
            every { getString(any()) } returns errorMessage
        }
        val mockGamesRepository = mockk<GamesRepository> {
            coEvery { getGamesFilteredByProviders(any()) } returns Result.error(GamesLoadingError.NOT_FOUND)
        }
        val fakeProviders = listOf("provider1", "provider2", "provider3")
        runBlockingTest {
            LoadingByProvidersUseCase(
                TestCoroutineDispatcher(),
                mockGamesRepository,
                mockContext
            ).invoke(fakeProviders) shouldBe MainFragmentState.Error(errorMessage)
        }
    }

    @Test
    fun `loading by providers with empty list works correctly`() {
        val errorMessage = "Error"
        val mockContext = mockk<Context> {
            every { getString(any()) } returns errorMessage
        }
        val mockGamesRepository = mockk<GamesRepository> {
            coEvery { getGamesFilteredByProviders(any()) } returns Result.success(emptyList())
        }
        val fakeProviders = listOf("provider1", "provider2", "provider3")
        runBlockingTest {
            LoadingByProvidersUseCase(
                TestCoroutineDispatcher(),
                mockGamesRepository,
                mockContext
            ).invoke(fakeProviders) shouldBe MainFragmentState.Error(errorMessage)
        }
    }
}
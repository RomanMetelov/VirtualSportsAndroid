package com.example.virtualsportsandroid

import android.content.Context
import com.example.virtualsportsandroid.mainScreen.data.GamesRepository
import com.example.virtualsportsandroid.mainScreen.data.model.GamesLoadingError
import com.example.virtualsportsandroid.mainScreen.domain.NotFilteredGamesLoadingUseCase
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
internal class NotFilteredGamesLoadingUseCaseTest {

    @Test
    fun `not filtered games loading use case without errors works correctly`() {
        val mockContext = mockk<Context> {
            every { getString(any()) } returns "Error"
        }
        val fakeAllGamesList = listOf(
            GameModel("id1", "name1", "imageURL1"),
            GameModel("id2", "name2", "imageURL2"),
            GameModel("id3", "name3", "imageURL3"),
            GameModel("id4", "name4", "imageURL4"),
            GameModel("id5", "name5", "imageURL5")
        )
        val fakeTopGames = listOf(
            GameModel("id1", "name1", "imageURL1"),
            GameModel("id2", "name2", "imageURL2"),
            GameModel("id3", "name3", "imageURL3")
        )
        val mockGamesRepository = mockk<GamesRepository> {
            coEvery { getAllGames() } returns Result.success(fakeAllGamesList)
            coEvery { getTopGames() } returns Result.success(fakeTopGames)
        }
        val expectedResult = MainFragmentState.NotFiltered(
            fakeTopGames,
            fakeAllGamesList
        )
        runBlockingTest {
            NotFilteredGamesLoadingUseCase(
                TestCoroutineDispatcher(),
                mockGamesRepository,
                mockContext
            ).invoke() shouldBe expectedResult
        }
    }

    @Test
    fun `not filtered games loading use case with not found error in top games works correctly`() {
        val errorMessage = "Error"
        val mockContext = mockk<Context> {
            every { getString(any()) } returns errorMessage
        }
        val fakeAllGamesList = listOf(
            GameModel("id1", "name1", "imageURL1"),
            GameModel("id2", "name2", "imageURL2"),
            GameModel("id3", "name3", "imageURL3"),
            GameModel("id4", "name4", "imageURL4"),
            GameModel("id5", "name5", "imageURL5")
        )
        val mockGamesRepository = mockk<GamesRepository> {
            coEvery { getAllGames() } returns Result.success(fakeAllGamesList)
            coEvery { getTopGames() } returns Result.error(GamesLoadingError.NOT_FOUND)
        }
        val expectedResult = MainFragmentState.Error(errorMessage)
        runBlockingTest {
            NotFilteredGamesLoadingUseCase(
                TestCoroutineDispatcher(),
                mockGamesRepository,
                mockContext
            ).invoke() shouldBe expectedResult
        }
    }

    @Test
    fun `not filtered games loading use case with not found error in all games works correctly`() {
        val errorMessage = "Error"
        val mockContext = mockk<Context> {
            every { getString(any()) } returns errorMessage
        }
        val fakeTopGamesList = listOf(
            GameModel("id1", "name1", "imageURL1"),
            GameModel("id2", "name2", "imageURL2"),
            GameModel("id3", "name3", "imageURL3")
        )
        val mockGamesRepository = mockk<GamesRepository> {
            coEvery { getAllGames() } returns Result.error(GamesLoadingError.NOT_FOUND)
            coEvery { getTopGames() } returns Result.success(fakeTopGamesList)
        }
        val expectedResult = MainFragmentState.Error(errorMessage)
        runBlockingTest {
            NotFilteredGamesLoadingUseCase(
                TestCoroutineDispatcher(),
                mockGamesRepository,
                mockContext
            ).invoke() shouldBe expectedResult
        }
    }
}
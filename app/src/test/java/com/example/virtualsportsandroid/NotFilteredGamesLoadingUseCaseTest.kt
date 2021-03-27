package com.example.virtualsportsandroid

import com.example.virtualsportsandroid.games.data.GamesRepository
import com.example.virtualsportsandroid.games.data.GamesLoadingError
import com.example.virtualsportsandroid.games.domain.NotFilteredGamesLoadingUseCase
import com.example.virtualsportsandroid.games.domain.model.GameModel
import com.example.virtualsportsandroid.games.domain.model.GamesList
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
internal class NotFilteredGamesLoadingUseCaseTest {

    @Test
    fun `not filtered games loading use case without errors works correctly`() {
        val fakeFirstTag = GamesList(
            "Top",
            listOf(
                GameModel("id1", "name1", "imageURL1"),
                GameModel("id2", "name2", "imageURL2"),
                GameModel("id3", "name3", "imageURL3")
            )
        )
        val fakeAllTagsWithoutFirst = listOf(
            GamesList("Favorites", listOf(
                GameModel("id4", "name4", "imageURL4"),
                GameModel("id5", "name5", "imageURL5")
            )),
            GamesList("Recently launched", listOf(
                GameModel("id6", "name6", "imageURL6"),
                GameModel("id7", "name7", "imageURL7")
            ))
        )
        val mockGamesRepository = mockk<GamesRepository> {
            coEvery { getAllGamesWithoutFirstTag() } returns Result.success(fakeAllTagsWithoutFirst)
            coEvery { getGamesWithFirstTag() } returns Result.success(fakeFirstTag)
        }
        val expectedResult = GamesFragmentState.NotFiltered(
            fakeFirstTag,
            fakeAllTagsWithoutFirst
        )
        runBlockingTest {
            NotFilteredGamesLoadingUseCase(
                TestCoroutineDispatcher(),
                mockGamesRepository
            ).invoke() shouldBe expectedResult
        }
    }

    @Test
    fun `not filtered games loading use case with not found error in top games works correctly`() {
        val fakeAllTagsWithoutFirst = listOf(
            GamesList("Favorites", listOf(
                GameModel("id1", "name1", "imageURL1"),
                GameModel("id2", "name2", "imageURL2")
            )),
            GamesList("Recently launched", listOf(
                GameModel("id3", "name3", "imageURL3"),
                GameModel("id4", "name4", "imageURL4")
            ))
        )
        val mockGamesRepository = mockk<GamesRepository> {
            coEvery { getAllGamesWithoutFirstTag() } returns Result.success(fakeAllTagsWithoutFirst)
            coEvery { getGamesWithFirstTag() } returns Result.error(GamesLoadingError.NOT_FOUND)
        }
        val expectedResult = GamesFragmentState.Error
        runBlockingTest {
            NotFilteredGamesLoadingUseCase(
                TestCoroutineDispatcher(),
                mockGamesRepository
            ).invoke() shouldBe expectedResult
        }
    }

    @Test
    fun `not filtered games loading use case with not found error in all games works correctly`() {
        val fakeFirstTag = GamesList(
            "Top",
            listOf(
                GameModel("id1", "name1", "imageURL1"),
                GameModel("id2", "name2", "imageURL2"),
                GameModel("id3", "name3", "imageURL3")
            )
        )
        val mockGamesRepository = mockk<GamesRepository> {
            coEvery { getAllGamesWithoutFirstTag() } returns Result.error(GamesLoadingError.NOT_FOUND)
            coEvery { getGamesWithFirstTag() } returns Result.success(fakeFirstTag)
        }
        val expectedResult = GamesFragmentState.Error
        runBlockingTest {
            NotFilteredGamesLoadingUseCase(
                TestCoroutineDispatcher(),
                mockGamesRepository
            ).invoke() shouldBe expectedResult
        }
    }
}
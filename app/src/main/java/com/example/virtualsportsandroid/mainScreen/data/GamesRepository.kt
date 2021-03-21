package com.example.virtualsportsandroid.mainScreen.data

import com.example.virtualsportsandroid.mainScreen.data.model.GameResponse
import com.example.virtualsportsandroid.mainScreen.data.model.GamesLoadingError
import com.example.virtualsportsandroid.mainScreen.data.model.GamesResponse
import com.example.virtualsportsandroid.mainScreen.domain.FilterByCategoryUseCase
import com.example.virtualsportsandroid.mainScreen.domain.FilterByCategoryAndProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.FilterByProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.model.GameModel
import com.example.virtualsportsandroid.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject


@Suppress("MagicNumber")
class GamesRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val filterByCategoryUseCase: FilterByCategoryUseCase,
    private val filterByProvidersUseCase: FilterByProvidersUseCase,
    private val filterByCategoryAndProvidersUseCase: FilterByCategoryAndProvidersUseCase
) {

    private companion object {
        const val TOP_GAME_CATEGORY = "top"
    }

    suspend fun getTopGames(): Result<List<GameModel>, GamesLoadingError> =
        withContext(dispatcher) {
            delay(2000) //emulation loading from network
            Result.success(
                filterByCategoryUseCase(
                    TOP_GAME_CATEGORY,
                    GamesResponse(
                        listOf(
                            GameResponse(
                                "id1",
                                "Game 1",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider1",
                                listOf("top")
                            ),
                            GameResponse(
                                "id2",
                                "Game 2",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider1",
                                listOf("top")
                            ),
                            GameResponse(
                                "id3",
                                "Game 3",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider2",
                                listOf("top")
                            ),
                            GameResponse(
                                "id4",
                                "Game 4",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider3",
                                listOf("top")
                            ),
                        )
                    )
                )
            )
        }

    suspend fun getGamesFilteredByCategory(category: String): Result<List<GameModel>, GamesLoadingError> =
        withContext(dispatcher) {
            delay(2000) //emulation loading from network
            Result.success(
                filterByCategoryUseCase(
                    category,
                    GamesResponse(
                        listOf(
                            GameResponse(
                                "id1",
                                "Game 1",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider1",
                                listOf("category1")
                            ),
                            GameResponse(
                                "id2",
                                "Game 2",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider1",
                                listOf("category1")
                            ),
                            GameResponse(
                                "id3",
                                "Game 3",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider2",
                                listOf("category2")
                            ),
                            GameResponse(
                                "id4",
                                "Game 4",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider3",
                                listOf("category2")
                            ),
                        )
                    )
                )
            )
        }

    suspend fun getGamesFilteredByProviders(providers: List<String>): Result<List<GameModel>, GamesLoadingError> =
        withContext(dispatcher) {
            delay(2000) //emulation loading from network
            Result.success(
                filterByProvidersUseCase(
                    providers,
                    GamesResponse(
                        listOf(
                            GameResponse(
                                "id1",
                                "Game 1",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider1",
                                listOf("category1")
                            ),
                            GameResponse(
                                "id2",
                                "Game 2",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider1",
                                listOf("category1")
                            ),
                            GameResponse(
                                "id3",
                                "Game 3",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider2",
                                listOf("category2")
                            ),
                            GameResponse(
                                "id4",
                                "Game 4",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider3",
                                listOf("category2")
                            ),
                        )
                    )
                )
            )
        }

    suspend fun getGamesFilteredByCategoryAndProviders(
        category: String,
        providers: List<String>
    ): Result<List<GameModel>, GamesLoadingError> =
        withContext(dispatcher) {
            delay(2000) //emulation loading from network
            Result.success(
                filterByCategoryAndProvidersUseCase(
                    category,
                    providers,
                    GamesResponse(
                        listOf(
                            GameResponse(
                                "id1",
                                "Game 1",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider1",
                                listOf("category1")
                            ),
                            GameResponse(
                                "id2",
                                "Game 2",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider1",
                                listOf("category1")
                            ),
                            GameResponse(
                                "id3",
                                "Game 3",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider2",
                                listOf("category2")
                            ),
                            GameResponse(
                                "id4",
                                "Game 4",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider3",
                                listOf("category2")
                            ),
                        )
                    )
                )
            )
        }

    suspend fun getAllGames(): Result<List<GameModel>, GamesLoadingError> =
        withContext(dispatcher) {
            delay(2000) //emulation loading from network
            Result.success(
                listOf(
                    GameResponse(
                        "id1",
                        "Game 1",
                        "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                        "provider1",
                        listOf("category1")
                    ),
                    GameResponse(
                        "id2",
                        "Game 2",
                        "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                        "provider1",
                        listOf("category1")
                    ),
                    GameResponse(
                        "id3",
                        "Game 3",
                        "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                        "provider2",
                        listOf("category2")
                    ),
                    GameResponse(
                        "id4",
                        "Game 4",
                        "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                        "provider1",
                        listOf("category1")
                    ),
                    GameResponse(
                        "id5",
                        "Game 5",
                        "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                        "provider3",
                        listOf("category1")
                    ),
                    GameResponse(
                        "id6",
                        "Game 6",
                        "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                        "provider4",
                        listOf("category4")
                    )
                ).map { GameModel(it.id, it.displayName, it.imageURL) }
            )
        }
}

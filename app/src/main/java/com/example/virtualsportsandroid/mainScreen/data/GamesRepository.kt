package com.example.virtualsportsandroid.mainScreen.data

import com.example.virtualsportsandroid.mainScreen.data.model.GameResponse
import com.example.virtualsportsandroid.mainScreen.data.model.GamesLoadingError
import com.example.virtualsportsandroid.mainScreen.data.model.GamesResponse
import com.example.virtualsportsandroid.mainScreen.domain.FilterByCategoryUseCase
import com.example.virtualsportsandroid.mainScreen.domain.FilterByCategoryAndProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.FilterByProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.FilterByTagUseCase
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
    private val filterByCategoryAndProvidersUseCase: FilterByCategoryAndProvidersUseCase,
    private val filterByTagUseCase: FilterByTagUseCase
) {

    private companion object {
        const val TOP_GAME_TAG = "top"
        const val ALL_GAME_TAG = "all"
    }

    suspend fun getTopGames(): Result<List<GameModel>, GamesLoadingError> =
        withContext(dispatcher) {
            delay(2000) //emulation loading from network
            Result.success(
                filterByTagUseCase(
                    TOP_GAME_TAG,
                    GamesResponse(
                        listOf(
                            GameResponse(
                                "id1",
                                "Game 1",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider1",
                                listOf("category1"),
                                listOf("top")
                            ),
                            GameResponse(
                                "id2",
                                "Game 2",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider1",
                                listOf("category2"),
                                listOf("top")
                            ),
                            GameResponse(
                                "id3",
                                "Game 3",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider2",
                                listOf("category3"),
                                listOf("top")
                            ),
                            GameResponse(
                                "id4",
                                "Game 4",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider3",
                                listOf("category4"),
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
                                listOf("category1"),
                                listOf("all")
                            ),
                            GameResponse(
                                "id2",
                                "Game 2",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider1",
                                listOf("category1"),
                                listOf("all")
                            ),
                            GameResponse(
                                "id3",
                                "Game 3",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider2",
                                listOf("category2"),
                                listOf("all")
                            ),
                            GameResponse(
                                "id4",
                                "Game 4",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider3",
                                listOf("category2"),
                                listOf("all")
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
                                listOf("category1"),
                                listOf("all")
                            ),
                            GameResponse(
                                "id2",
                                "Game 2",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider1",
                                listOf("category1"),
                                listOf("all")
                            ),
                            GameResponse(
                                "id3",
                                "Game 3",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider2",
                                listOf("category2"),
                                listOf("all")
                            ),
                            GameResponse(
                                "id4",
                                "Game 4",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider3",
                                listOf("category2"),
                                listOf("all")
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
                                listOf("category1"),
                                listOf("all")
                            ),
                            GameResponse(
                                "id2",
                                "Game 2",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider1",
                                listOf("category1"),
                                listOf("all")
                            ),
                            GameResponse(
                                "id3",
                                "Game 3",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider2",
                                listOf("category2"),
                                listOf("all")
                            ),
                            GameResponse(
                                "id4",
                                "Game 4",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider3",
                                listOf("category2"),
                                listOf("all")
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
                filterByTagUseCase(
                    ALL_GAME_TAG,
                    GamesResponse(
                        listOf(
                            GameResponse(
                                "id1",
                                "Game 1",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider1",
                                listOf("category1"),
                                listOf("all")
                            ),
                            GameResponse(
                                "id2",
                                "Game 2",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider1",
                                listOf("category1"),
                                listOf("all")
                            ),
                            GameResponse(
                                "id3",
                                "Game 3",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider2",
                                listOf("category2"),
                                listOf("all")
                            ),
                            GameResponse(
                                "id4",
                                "Game 4",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider1",
                                listOf("category1"),
                                listOf("all")
                            ),
                            GameResponse(
                                "id5",
                                "Game 5",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider3",
                                listOf("category1"),
                                listOf("all")
                            ),
                            GameResponse(
                                "id6",
                                "Game 6",
                                "https://ubuntupit.com/wp-content/uploads/2019/07/Football-Strike.png",
                                "provider4",
                                listOf("category4"),
                                listOf("all")
                            )
                        )
                    )
                ).map { GameModel(it.id, it.displayName, it.imageURL) }
            )
        }
}

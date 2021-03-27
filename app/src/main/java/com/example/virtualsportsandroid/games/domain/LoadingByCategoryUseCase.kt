package com.example.virtualsportsandroid.games.domain
import com.example.virtualsportsandroid.games.data.GamesRepository
import com.example.virtualsportsandroid.games.ui.GamesFragmentState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LoadingByCategoryUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val gamesRepository: GamesRepository
) {
    suspend operator fun invoke(category: String): GamesFragmentState = withContext(dispatcher) {
        val result = gamesRepository.getGamesFilteredByCategory(category)
        when {
            result.isError -> {
                GamesFragmentState.Error
            }
            result.successResult.isEmpty() -> {
                GamesFragmentState.Error
            }
            else -> {
                val categoryNameResult = gamesRepository.getCategoryName(category)
                if (categoryNameResult.isError) {
                    GamesFragmentState.Error
                } else {
                    GamesFragmentState.FilteredByCategory(
                        categoryNameResult.successResult,
                        result.successResult
                    )
                }
            }
        }
    }
}

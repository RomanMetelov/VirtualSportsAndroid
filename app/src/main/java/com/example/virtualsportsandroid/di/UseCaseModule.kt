package com.example.virtualsportsandroid.di

import com.example.virtualsportsandroid.dices.game.data.DiceGameResultRepository
import com.example.virtualsportsandroid.dices.game.domain.FromApiToUiMapper
import com.example.virtualsportsandroid.dices.game.ui.DiceGameResultLoadingUseCase
import com.example.virtualsportsandroid.dices.history.DiceGameBetHistoryLoadingUseCase
import com.example.virtualsportsandroid.dices.history.data.DiceGameBetHistoryRepository
import com.example.virtualsportsandroid.filter.data.FiltersRepository
import com.example.virtualsportsandroid.filter.domain.FiltersLoadingUseCase
import com.example.virtualsportsandroid.games.data.GamesRepository
import com.example.virtualsportsandroid.games.domain.LoadingByProvidersUseCase
import com.example.virtualsportsandroid.games.domain.LoadingByCategoryUseCase
import com.example.virtualsportsandroid.games.domain.LoadingByCategoryAndProvidersUseCase
import com.example.virtualsportsandroid.games.domain.FilterByCategoryAndProvidersUseCase
import com.example.virtualsportsandroid.games.domain.FilterByCategoryUseCase
import com.example.virtualsportsandroid.games.domain.FilterByProvidersUseCase
import com.example.virtualsportsandroid.games.domain.FilterByTagUseCase
import com.example.virtualsportsandroid.games.domain.NotFilteredGamesLoadingUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Suppress("TooManyFunctions")
@Module(includes = [RepositoryModule::class])
class UseCaseModule {

    @Provides
    @Singleton
    fun provideFilterByCategoryUseCase(): FilterByCategoryUseCase {
        return FilterByCategoryUseCase(Dispatchers.Default)
    }

    @Provides
    @Singleton
    fun provideFilterByProvidersUseCase(): FilterByProvidersUseCase {
        return FilterByProvidersUseCase(Dispatchers.Default)
    }

    @Provides
    @Singleton
    fun provideFilterByCategoryAndProvidersUseCase(): FilterByCategoryAndProvidersUseCase {
        return FilterByCategoryAndProvidersUseCase(Dispatchers.Default)
    }

    @Provides
    @Singleton
    fun provideFilterByTagUseCase(): FilterByTagUseCase {
        return FilterByTagUseCase(Dispatchers.Default)
    }

    @Provides
    @Singleton
    fun provideNotFilteredGamesLoadingUseCase(
        gamesRepository: GamesRepository
    ): NotFilteredGamesLoadingUseCase {
        return NotFilteredGamesLoadingUseCase(Dispatchers.Default, gamesRepository)
    }

    @Provides
    @Singleton
    fun provideFilteredGamesByCategoryLoadingUseCase(
        gamesRepository: GamesRepository
    ): LoadingByCategoryUseCase {
        return LoadingByCategoryUseCase(Dispatchers.IO, gamesRepository)
    }

    @Provides
    @Singleton
    fun provideFilteredGamesByProvidersLoadingUseCase(
        gamesRepository: GamesRepository
    ): LoadingByProvidersUseCase {
        return LoadingByProvidersUseCase(Dispatchers.IO, gamesRepository)
    }

    @Provides
    @Singleton
    fun provideLoadingByCategoryAndProvidersUseCase(
        gamesRepository: GamesRepository
    ): LoadingByCategoryAndProvidersUseCase {
        return LoadingByCategoryAndProvidersUseCase(Dispatchers.IO, gamesRepository)
    }

    @Provides
    @Singleton
    fun provideFiltersLoadingUseCase(filtersRepository: FiltersRepository): FiltersLoadingUseCase {
        return FiltersLoadingUseCase(Dispatchers.Default, filtersRepository)
    }

    @Provides
    @Singleton
    fun provideDiceGameBetHistoryLoadingUseCase(
        diceGameBetHistoryRepository: DiceGameBetHistoryRepository
    ): DiceGameBetHistoryLoadingUseCase {
        return DiceGameBetHistoryLoadingUseCase(Dispatchers.Default, diceGameBetHistoryRepository)
    }

    @Provides
    @Singleton
    fun provideDiceGameResultLoadingUseCase(
        diceGameResultRepository: DiceGameResultRepository,
        fromApiToUiMapper: FromApiToUiMapper
    ): DiceGameResultLoadingUseCase {
        return DiceGameResultLoadingUseCase(
            dispatcher = Dispatchers.Default,
            diceGameResultRepository = diceGameResultRepository,
            fromApiToUiMapper = fromApiToUiMapper
        )
    }
}

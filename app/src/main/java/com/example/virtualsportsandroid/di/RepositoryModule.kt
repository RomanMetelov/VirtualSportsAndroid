package com.example.virtualsportsandroid.di


import android.content.Context
import com.example.virtualsportsandroid.dices.game.data.DiceGameResultRepository
import com.example.virtualsportsandroid.dices.history.data.DiceGameBetHistoryRepository
import com.example.virtualsportsandroid.filter.data.FiltersRepository
import com.example.virtualsportsandroid.games.data.GamesRepository
import com.example.virtualsportsandroid.games.domain.FilterByCategoryAndProvidersUseCase
import com.example.virtualsportsandroid.games.domain.FilterByCategoryUseCase
import com.example.virtualsportsandroid.games.domain.FilterByProvidersUseCase
import com.example.virtualsportsandroid.games.domain.FilterByTagUseCase
import com.example.virtualsportsandroid.main.data.GamesInfoService
import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module(includes = [UseCaseModule::class, AppModule::class, NetworkModule::class])
class RepositoryModule {

    @Suppress("LongParameterList")
    @Singleton
    @Provides
    fun provideGamesRepository(
        filterByCategoryUseCase: FilterByCategoryUseCase,
        filterByProvidersUseCase: FilterByProvidersUseCase,
        filterByCategoryAndProvidersUseCase: FilterByCategoryAndProvidersUseCase,
        filterByTagUseCase: FilterByTagUseCase,
        sharedPref: SharedPref,
        gson: Gson
    ): GamesRepository {
        return GamesRepository(
            Dispatchers.IO,
            filterByCategoryUseCase,
            filterByProvidersUseCase,
            filterByCategoryAndProvidersUseCase,
            filterByTagUseCase,
            sharedPref,
            gson
        )
    }

    @Singleton
    @Provides
    fun provideFiltersRepository(
        sharedPref: SharedPref,
        gson: Gson,
        context: Context
    ): FiltersRepository {
        return FiltersRepository(Dispatchers.IO, sharedPref, gson, context)
    }

    @Singleton
    @Provides
    fun provideGamesInfoRepository(
        sharedPref: SharedPref,
        gson: Gson,
        gamesInfoService: GamesInfoService,
        context: Context
    ): com.example.virtualsportsandroid.main.data.GamesInfoRepository {
        return com.example.virtualsportsandroid.main.data.GamesInfoRepository(
            Dispatchers.IO,
            sharedPref,
            gson,
            gamesInfoService,
            context
        )
    }

    @Singleton
    @Provides
    fun provideDiceGameBetHistoryRepository(): DiceGameBetHistoryRepository {
        return DiceGameBetHistoryRepository(Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun provideDiceGameResultRepository(): DiceGameResultRepository {
        return DiceGameResultRepository(Dispatchers.IO)
    }
}

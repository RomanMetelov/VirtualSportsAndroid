package com.example.virtualsportsandroid.di

import android.content.Context
import com.example.virtualsportsandroid.mainScreen.data.GamesRepository
import com.example.virtualsportsandroid.mainScreen.domain.*
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module(includes = [RepositoryModule::class, AppModule::class])
class UseCaseModule {

    @Provides
    @Singleton
    fun filterByCategoryUseCase(): FilterByCategoryUseCase {
        return FilterByCategoryUseCase(Dispatchers.Default)
    }

    @Provides
    @Singleton
    fun filterByProvidersUseCase(): FilterByProvidersUseCase {
        return FilterByProvidersUseCase(Dispatchers.Default)
    }

    @Provides
    @Singleton
    fun filterByCategoryAndProvidersUseCase(): FilterByCategoryAndProvidersUseCase {
        return FilterByCategoryAndProvidersUseCase(Dispatchers.Default)
    }

    @Provides
    @Singleton
    fun notFilteredGamesLoadingUseCase(
        gamesRepository: GamesRepository,
        context: Context
    ): NotFilteredGamesLoadingUseCase {
        return NotFilteredGamesLoadingUseCase(Dispatchers.Default, gamesRepository, context)
    }

    @Provides
    @Singleton
    fun filteredGamesByCategoryLoadingUseCase(
        gamesRepository: GamesRepository,
        context: Context
    ): LoadingByCategoryUseCase {
        return LoadingByCategoryUseCase(Dispatchers.IO, gamesRepository, context)
    }

    @Provides
    @Singleton
    fun filteredGamesByProvidersLoadingUseCase(
        gamesRepository: GamesRepository,
        context: Context
    ): LoadingByProvidersUseCase {
        return LoadingByProvidersUseCase(Dispatchers.IO, gamesRepository, context)
    }

    @Provides
    @Singleton
    fun loadingByCategoryAndProvidersUseCase(
        gamesRepository: GamesRepository,
        context: Context
    ): LoadingByCategoryAndProvidersUseCase {
        return LoadingByCategoryAndProvidersUseCase(Dispatchers.IO, gamesRepository, context)
    }
}
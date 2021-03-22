package com.example.virtualsportsandroid.di

import android.content.Context
import com.example.virtualsportsandroid.filter.data.FiltersRepository
import com.example.virtualsportsandroid.filter.domain.FiltersLoadingUseCase
import com.example.virtualsportsandroid.mainScreen.data.GamesRepository
import com.example.virtualsportsandroid.mainScreen.domain.FilterByTagUseCase
import com.example.virtualsportsandroid.mainScreen.domain.FilterByCategoryUseCase
import com.example.virtualsportsandroid.mainScreen.domain.FilterByProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.FilterByCategoryAndProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.LoadingByCategoryUseCase
import com.example.virtualsportsandroid.mainScreen.domain.LoadingByProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.LoadingByCategoryAndProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.NotFilteredGamesLoadingUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module(includes = [RepositoryModule::class, AppModule::class])
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
        gamesRepository: GamesRepository,
        context: Context
    ): NotFilteredGamesLoadingUseCase {
        return NotFilteredGamesLoadingUseCase(Dispatchers.Default, gamesRepository, context)
    }

    @Provides
    @Singleton
    fun provideFilteredGamesByCategoryLoadingUseCase(
        gamesRepository: GamesRepository,
        context: Context
    ): LoadingByCategoryUseCase {
        return LoadingByCategoryUseCase(Dispatchers.IO, gamesRepository, context)
    }

    @Provides
    @Singleton
    fun provideFilteredGamesByProvidersLoadingUseCase(
        gamesRepository: GamesRepository,
        context: Context
    ): LoadingByProvidersUseCase {
        return LoadingByProvidersUseCase(Dispatchers.IO, gamesRepository, context)
    }

    @Provides
    @Singleton
    fun provideLoadingByCategoryAndProvidersUseCase(
        gamesRepository: GamesRepository,
        context: Context
    ): LoadingByCategoryAndProvidersUseCase {
        return LoadingByCategoryAndProvidersUseCase(Dispatchers.IO, gamesRepository, context)
    }

    @Provides
    @Singleton
    fun provideFiltersLoadingUseCase(filtersRepository: FiltersRepository): FiltersLoadingUseCase {
        return FiltersLoadingUseCase(Dispatchers.Default, filtersRepository)
    }
}

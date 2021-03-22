package com.example.virtualsportsandroid.di

import com.example.virtualsportsandroid.filter.data.FiltersRepository
import com.example.virtualsportsandroid.mainScreen.data.GamesRepository
import com.example.virtualsportsandroid.mainScreen.domain.FilterByCategoryAndProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.FilterByCategoryUseCase
import com.example.virtualsportsandroid.mainScreen.domain.FilterByProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.FilterByTagUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module(includes = [UseCaseModule::class])
class RepositoryModule {

    @Singleton
    @Provides
    fun provideGamesRepository(
        filterByCategoryUseCase: FilterByCategoryUseCase,
        filterByProvidersUseCase: FilterByProvidersUseCase,
        filterByCategoryAndProvidersUseCase: FilterByCategoryAndProvidersUseCase,
        filterByTagUseCase: FilterByTagUseCase
    ): GamesRepository {
        return GamesRepository(
            Dispatchers.IO,
            filterByCategoryUseCase,
            filterByProvidersUseCase,
            filterByCategoryAndProvidersUseCase,
            filterByTagUseCase
        )
    }

    @Singleton
    @Provides
    fun provideFiltersRepository(): FiltersRepository {
        return FiltersRepository(Dispatchers.IO)
    }
}

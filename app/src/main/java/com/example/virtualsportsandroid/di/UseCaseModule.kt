package com.example.virtualsportsandroid.di

import com.example.virtualsportsandroid.mainScreen.data.GamesRepository
import com.example.virtualsportsandroid.mainScreen.domain.FilterByCategoryAndProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.FilterByCategoryUseCase
import com.example.virtualsportsandroid.mainScreen.domain.FilterByProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.NotFilteredGamesLoadingUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module(includes = [RepositoryModule::class])
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
    fun notFilteredGamesLoadingUseCase(gamesRepository: GamesRepository): NotFilteredGamesLoadingUseCase {
        return NotFilteredGamesLoadingUseCase(Dispatchers.Default, gamesRepository)
    }
}
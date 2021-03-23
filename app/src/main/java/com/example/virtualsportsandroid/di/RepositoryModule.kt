package com.example.virtualsportsandroid.di

import android.content.Context
import com.example.virtualsportsandroid.filter.data.FiltersRepository
import com.example.virtualsportsandroid.loadingConfigs.data.ConfigsRepository
import com.example.virtualsportsandroid.mainScreen.data.GamesRepository
import com.example.virtualsportsandroid.mainScreen.domain.FilterByCategoryAndProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.FilterByCategoryUseCase
import com.example.virtualsportsandroid.mainScreen.domain.FilterByProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.FilterByTagUseCase
import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module(includes = [UseCaseModule::class, AppModule::class])
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
    fun provideConfigsRepository(sharedPref: SharedPref, gson: Gson): ConfigsRepository {
        return ConfigsRepository(Dispatchers.IO, sharedPref, gson)
    }
}

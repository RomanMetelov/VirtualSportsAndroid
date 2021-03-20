package com.example.virtualsportsandroid.di

import com.example.virtualsportsandroid.mainScreen.domain.LoadingByCategoryAndProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.LoadingByCategoryUseCase
import com.example.virtualsportsandroid.mainScreen.domain.LoadingByProvidersUseCase
import com.example.virtualsportsandroid.mainScreen.domain.NotFilteredGamesLoadingUseCase
import com.example.virtualsportsandroid.mainScreen.ui.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Singleton
    @Provides
    fun mainViewModel(
        notFilteredGamesLoadingUseCase: NotFilteredGamesLoadingUseCase,
        loadingByCategoryUseCase: LoadingByCategoryUseCase,
        loadingByProvidersUseCase: LoadingByProvidersUseCase,
        loadingByCategoryAndProvidersUseCase: LoadingByCategoryAndProvidersUseCase
    ): MainViewModel {
        return MainViewModel(
            notFilteredGamesLoadingUseCase,
            loadingByCategoryUseCase,
            loadingByProvidersUseCase,
            loadingByCategoryAndProvidersUseCase
        )
    }
}
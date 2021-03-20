package com.example.virtualsportsandroid.di

import com.example.virtualsportsandroid.mainScreen.domain.NotFilteredGamesLoadingUseCase
import com.example.virtualsportsandroid.mainScreen.ui.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Singleton
    @Provides
    fun mainViewModel(notFilteredGamesLoadingUseCase: NotFilteredGamesLoadingUseCase): MainViewModel {
        return MainViewModel(notFilteredGamesLoadingUseCase)
    }
}
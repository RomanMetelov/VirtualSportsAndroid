package com.example.virtualsportsandroid.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.virtualsportsandroid.dices.game.ui.DiceGameViewModel
import com.example.virtualsportsandroid.dices.history.ui.DiceGameBetHistoryViewModel
import com.example.virtualsportsandroid.filter.ui.FilterViewModel
import com.example.virtualsportsandroid.game.ui.GameFragmentViewModel
import com.example.virtualsportsandroid.games.ui.GamesViewModel
import com.example.virtualsportsandroid.login.ui.LoginViewModel
import com.example.virtualsportsandroid.main.ui.MainViewModel
import com.example.virtualsportsandroid.registration.ui.RegistrationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DiceGameViewModel::class)
    abstract fun diceGameViewModel(diceGameViewModel: DiceGameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DiceGameBetHistoryViewModel::class)
    abstract fun diceGameBetHistoryViewModel(diceGameBetHistoryViewModel: DiceGameBetHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FilterViewModel::class)
    abstract fun filterViewModel(filterViewModel: FilterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GameFragmentViewModel::class)
    abstract fun gameFragmentViewModel(gameFragmentViewModel: GameFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GamesViewModel::class)
    abstract fun gamesViewModel(gamesViewModel: GamesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun loginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    abstract fun registrationViewModel(registrationViewModel: RegistrationViewModel): ViewModel
}

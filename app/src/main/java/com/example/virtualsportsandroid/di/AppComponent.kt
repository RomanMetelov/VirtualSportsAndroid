@file:Suppress("MatchingDeclarationName")

package com.example.virtualsportsandroid.di


import com.example.virtualsportsandroid.dices.game.ui.DiceGameFragment
import com.example.virtualsportsandroid.dices.history.ui.DiceGameBetHistoryFragment
import com.example.virtualsportsandroid.game.ui.GameFragment
import com.example.virtualsportsandroid.filter.ui.FilterFragment
import com.example.virtualsportsandroid.login.ui.LoginFragment
import com.example.virtualsportsandroid.login.ui.RegistrationFragment
import com.example.virtualsportsandroid.mainScreen.ui.MainFragment
import com.example.virtualsportsandroid.nonetwork.NetworkErrorFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, RepositoryModule::class, UseCaseModule::class, NetworkModule::class])
@Singleton
interface AppComponent {

    fun inject(registrationFragment: RegistrationFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(networkErrorFragment: NetworkErrorFragment)
    fun inject(mainFragment: MainFragment)
    fun inject(gameFragment: GameFragment)
    fun inject(filterFragment: FilterFragment)
    fun inject(diceGameFragment: DiceGameFragment)
    fun inject(diceGameBetHistoryFragment: DiceGameBetHistoryFragment)
}

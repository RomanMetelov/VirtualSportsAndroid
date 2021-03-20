@file:Suppress("MatchingDeclarationName")
package com.example.virtualsportsandroid.di

import android.content.Context
import com.example.virtualsportsandroid.dices.DiceGameBetHistoryFragment
import com.example.virtualsportsandroid.dices.DiceGameFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun context(): Context

    fun inject(fragment: DiceGameBetHistoryFragment)

    fun inject(fragment: DiceGameFragment)

}

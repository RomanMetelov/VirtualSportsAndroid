package com.example.virtualsportsandroid.utils

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.example.virtualsportsandroid.dices.DiceGameBetHistoryModel
import com.example.virtualsportsandroid.dices.history.ui.DiceGameBetHistoryFragment
import com.example.virtualsportsandroid.dices.game.ui.DiceGameFragment
import com.example.virtualsportsandroid.game.data.ScreenGameModel
import com.example.virtualsportsandroid.game.ui.GameFragment
import com.example.virtualsportsandroid.loadingConfigs.ui.LoadingFragment
import com.example.virtualsportsandroid.login.ui.LoginFragment
import com.example.virtualsportsandroid.registration.ui.RegistrationFragment
import com.example.virtualsportsandroid.main.ui.MainFragment
import com.example.virtualsportsandroid.nonetwork.NetworkErrorFragment

class FragmentNavigator(
    private val fragmentManager: FragmentManager,
    @IdRes private val container: Int,
) {
    fun showLoginFragment() {
        fragmentManager.beginTransaction()
            .replace(container, LoginFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun showRegistrationFragment() {
        fragmentManager.beginTransaction()
            .replace(container, RegistrationFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun showMainFragment() {
        fragmentManager.beginTransaction()
            .replace(container, MainFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun showNoNetworkFragment() {
        fragmentManager.beginTransaction()
            .replace(container, NetworkErrorFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun showGameFragment(gameModel: ScreenGameModel) {
        fragmentManager.beginTransaction()
            .replace(container, GameFragment.newInstance(gameModel))
            .addToBackStack(null)
            .commit()
    }

    fun showConfigsLoadingFragment() {
        fragmentManager.beginTransaction()
            .replace(container, LoadingFragment.newInstance())
            .commit()
    }

    fun showDiceGameFragment() {
        fragmentManager.beginTransaction()
            .replace(container, DiceGameFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun showDiceGameBetHistoryFragment() {
        fragmentManager.beginTransaction()
            .replace(container, DiceGameBetHistoryFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun back() {
        fragmentManager.popBackStack()
    }
}

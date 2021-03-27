package com.example.virtualsportsandroid.utils

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.example.virtualsportsandroid.dices.history.ui.DiceGameBetHistoryFragment
import com.example.virtualsportsandroid.game.data.ScreenGameModel
import com.example.virtualsportsandroid.game.ui.GameFragment
import com.example.virtualsportsandroid.login.ui.LoginFragment
import com.example.virtualsportsandroid.main.ui.MainFragment
import com.example.virtualsportsandroid.nonetwork.NetworkErrorFragment
import com.example.virtualsportsandroid.registration.ui.RegistrationFragment

class FragmentNavigator(
    private val fragmentManager: FragmentManager,
    @IdRes private val container: Int,
) {
    fun showLoginFragment() {
        fragmentManager.beginTransaction()
            .replace(container, LoginFragment.newInstance())
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    fun showRegistrationFragment() {
        fragmentManager.beginTransaction()
            .replace(container, RegistrationFragment.newInstance())
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    fun showMainFragment() {
        fragmentManager.beginTransaction()
            .replace(container, MainFragment.newInstance())
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    fun showNoNetworkFragment() {
        fragmentManager.beginTransaction()
            .replace(container, NetworkErrorFragment.newInstance())
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    fun showGameFragment(gameModel: ScreenGameModel) {
        fragmentManager.beginTransaction()
            .replace(container, GameFragment.newInstance(gameModel))
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    fun showDiceGameBetHistoryFragment() {
        fragmentManager.beginTransaction()
            .replace(container, DiceGameBetHistoryFragment.newInstance())
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    fun back() {
        fragmentManager.popBackStack()
    }
}

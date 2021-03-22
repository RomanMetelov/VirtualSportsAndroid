package com.example.virtualsportsandroid.utils

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.example.virtualsportsandroid.filter.ui.FilterFragment
import com.example.virtualsportsandroid.game.data.ScreenGameModel
import com.example.virtualsportsandroid.game.ui.GameFragment
import com.example.virtualsportsandroid.login.ui.LoginFragment
import com.example.virtualsportsandroid.login.ui.RegistrationFragment
import com.example.virtualsportsandroid.mainScreen.ui.MainFragment
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

    fun showMainFragment(
        category: String? = null,
        providers: List<String>? = null
    ) {
        fragmentManager.beginTransaction()
            .replace(
                container,
                MainFragment.newInstance(category = category, providers = providers)
            )
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

    fun showFilterFragment() {
        fragmentManager.beginTransaction()
            .replace(container, FilterFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun back() {
        fragmentManager.popBackStack()
    }

}

package com.example.virtualsportsandroid.utils

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.example.virtualsportsandroid.filter.ui.FilterFragment
import com.example.virtualsportsandroid.game.data.ScreenGameModel
import com.example.virtualsportsandroid.game.ui.GameFragment
import com.example.virtualsportsandroid.loadingConfigs.ui.LoadingFragment
import com.example.virtualsportsandroid.login.ui.LoginFragment
import com.example.virtualsportsandroid.mainScreen.ui.MainFragment
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

    fun showFilterFragment() {
        fragmentManager.beginTransaction()
            .replace(container, FilterFragment.newInstance())
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    fun showConfigsLoadingFragment() {
        fragmentManager.beginTransaction()
            .replace(container, LoadingFragment.newInstance())
            .commitAllowingStateLoss()
    }

    fun back() {
        fragmentManager.popBackStack()
    }

}

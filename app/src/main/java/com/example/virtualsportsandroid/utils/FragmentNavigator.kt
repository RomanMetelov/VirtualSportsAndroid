package com.example.virtualsportsandroid.utils

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
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

    fun showMainFragmentWithoutFilters() {
        fragmentManager.beginTransaction()
            .replace(container, MainFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun showMainFragmentWithFilterByCategory(category: String) {
        fragmentManager.beginTransaction()
            .replace(container, MainFragment.newInstance(category))
            .addToBackStack(null)
            .commit()
    }

    fun showMainFragmentWithFilterByProviders(providers: List<String>) {
        fragmentManager.beginTransaction()
            .replace(container, MainFragment.newInstance(providers))
            .addToBackStack(null)
            .commit()
    }

    fun showMainFragmentWithFilterByCategoryAndProviders(
        category: String,
        providers: List<String>
    ) {
        fragmentManager.beginTransaction()
            .replace(container, MainFragment.newInstance(category, providers))
            .addToBackStack(null)
            .commit()
    }

    fun showNoNetworkFragment() {
        fragmentManager.beginTransaction()
            .replace(container, NetworkErrorFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }
}

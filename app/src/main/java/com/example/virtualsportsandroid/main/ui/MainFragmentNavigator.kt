package com.example.virtualsportsandroid.main.ui

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.example.virtualsportsandroid.filter.ui.FilterFragment
import com.example.virtualsportsandroid.games.ui.GamesFragment

class MainFragmentNavigator(
    private val fragmentManager: FragmentManager,
    @IdRes private val container: Int,
    private val setFragmentContainerStateToFilter: () -> Unit,
    private val setFragmentContainerStateToGames: (category: String?, providers: List<String>?) -> Unit
) {

    fun showFilterFragment() {
        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                container,
                FilterFragment.newInstance(::back, setFragmentContainerStateToGames)
            )
            .commitAllowingStateLoss()
    }

    fun showGamesFragment(category: String?, providers: List<String>?) {
        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                container,
                GamesFragment.newInstance(category, providers, setFragmentContainerStateToFilter)
            )
            .commitAllowingStateLoss()
    }

    private fun back() {
        fragmentManager.popBackStack()
    }
}

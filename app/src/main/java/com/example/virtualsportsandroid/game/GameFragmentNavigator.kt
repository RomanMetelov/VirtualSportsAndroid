package com.example.virtualsportsandroid.game

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.example.virtualsportsandroid.dices.game.ui.DiceGameFragment
import com.example.virtualsportsandroid.game.data.ScreenGameModel
import com.example.virtualsportsandroid.game.ui.WebViewFragment

class GameFragmentNavigator(
    private val fragmentManager: FragmentManager,
    @IdRes private val container: Int
) {

    fun showDiceFragment() {
        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                container,
                DiceGameFragment.newInstance()
            )
            .commitAllowingStateLoss()
    }

    fun showWebViewFragment(game: ScreenGameModel) {
        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                container,
                WebViewFragment.newInstance(game)
            )
            .commitAllowingStateLoss()
    }
}

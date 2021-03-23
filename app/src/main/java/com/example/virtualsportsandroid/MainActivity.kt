package com.example.virtualsportsandroid

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.virtualsportsandroid.game.data.ScreenGameModel
import com.example.virtualsportsandroid.utils.FragmentNavigator

class MainActivity : AppCompatActivity() {

    val fragmentNavigator by lazy {
        FragmentNavigator(
            supportFragmentManager,
            R.id.container
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        fragmentNavigator.showGameFragment(
            ScreenGameModel(
                "", "", "https://vsw.betradar.com/ls/mobile/?/parimatchrgs/ru/page/vsmobile_demo"
            )
        )
        fragmentNavigator.showLoginFragment()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //temp solution
        if (supportFragmentManager.backStackEntryCount < 1) {
            fragmentNavigator.showMainFragment()
        }
    }
}

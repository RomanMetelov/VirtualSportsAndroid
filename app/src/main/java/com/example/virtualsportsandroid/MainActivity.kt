package com.example.virtualsportsandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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

        fragmentNavigator.showMainFragmentWithNotFilteredGames()
    }


}

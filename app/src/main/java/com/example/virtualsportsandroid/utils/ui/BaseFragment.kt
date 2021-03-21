package com.example.virtualsportsandroid.utils.ui

import androidx.fragment.app.Fragment
import com.example.virtualsportsandroid.MainActivity
import com.example.virtualsportsandroid.utils.FragmentNavigator
import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import javax.inject.Inject


abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {

    @Inject
    lateinit var sharedPreferences: SharedPref

    protected val navigator: FragmentNavigator by lazy {
        (requireActivity() as MainActivity).fragmentNavigator
    }
}

package com.example.virtualsportsandroid.utils.ui

import android.content.Context
import android.net.ConnectivityManager
import androidx.fragment.app.Fragment
import com.example.virtualsportsandroid.MainActivity
import com.example.virtualsportsandroid.utils.FragmentNavigator


abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {

    protected val navigator: FragmentNavigator by lazy {
        (requireActivity() as MainActivity).fragmentNavigator
    }
}

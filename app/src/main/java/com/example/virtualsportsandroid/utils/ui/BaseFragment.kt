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

    protected fun isNetworkConnected(): Boolean {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        return activeNetwork != null
    }
}

package com.example.virtualsportsandroid.main.ui

sealed class MainFragmentContainerState {
    object Filter : MainFragmentContainerState()
    data class Games(val category: String?, val providers: List<String>?) :
        MainFragmentContainerState()
}

package com.example.virtualsportsandroid.main.ui

sealed class MainFragmentState {
    object Loading : MainFragmentState()
    object Content : MainFragmentState()
    object Error : MainFragmentState()
}

package com.example.virtualsportsandroid.main.ui

sealed class MainFragmentState {
    object Loading : MainFragmentState()
    object Content : MainFragmentState()
    data class Error(val errorMessage: String) : MainFragmentState()
}

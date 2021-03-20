package com.example.virtualsportsandroid.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.virtualsportsandroid.login.domain.CheckLoginInputsUseCase
import com.example.virtualsportsandroid.login.domain.LoginInputsError
import com.example.virtualsportsandroid.utils.Result
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private var checkLoginInputsUseCase: CheckLoginInputsUseCase
) : ViewModel() {

    private val _checkInputsLiveData = MutableLiveData<Result<Boolean, LoginInputsError>>()
    val checkInputsLiveData: LiveData<Result<Boolean, LoginInputsError>> =
        _checkInputsLiveData

    fun checkInputs(login: String, password: String) {
        _checkInputsLiveData.value = checkLoginInputsUseCase.invoke(
            login = login,
            password = password
        )
    }
}

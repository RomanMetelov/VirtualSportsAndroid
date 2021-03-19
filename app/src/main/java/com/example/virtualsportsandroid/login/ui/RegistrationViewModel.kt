package com.example.virtualsportsandroid.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.virtualsportsandroid.login.domain.CheckRegistrationInputsUseCase
import com.example.virtualsportsandroid.login.domain.RegistrationInputsError
import com.example.virtualsportsandroid.utils.Result
import javax.inject.Inject

class RegistrationViewModel
@Inject constructor(
    private var checkRegistrationInputsUseCase: CheckRegistrationInputsUseCase
) : ViewModel() {

    private val _checkInputsLiveData = MutableLiveData<Result<Boolean, RegistrationInputsError>>()
    val checkInputsLiveData: LiveData<Result<Boolean, RegistrationInputsError>> =
        _checkInputsLiveData

    fun checkInputs(login: String, password: String, repeatPassword: String) {
        _checkInputsLiveData.value = checkRegistrationInputsUseCase.invoke(
            login = login,
            password = password,
            repeatPassword = repeatPassword
        )
    }
}

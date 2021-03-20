package com.example.virtualsportsandroid.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.login.data.api.LoginUtils
import com.example.virtualsportsandroid.login.data.model.AccessTokenResponse
import com.example.virtualsportsandroid.login.data.model.UserModel
import com.example.virtualsportsandroid.login.domain.CheckLoginInputsUseCase
import com.example.virtualsportsandroid.login.domain.LoginInputsError
import com.example.virtualsportsandroid.utils.Result
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private typealias checkLoginInputsAlias = Result<Boolean, LoginInputsError>
private typealias loginTryAlias = Result<AccessTokenResponse, NetworkErrorType>

class LoginViewModel @Inject constructor(
    private val checkLoginInputsUseCase: CheckLoginInputsUseCase,
    private val loginUtils: LoginUtils
) : ViewModel() {

    private val _checkInputsLiveData = MutableLiveData<checkLoginInputsAlias>()
    val checkInputsLiveData: LiveData<checkLoginInputsAlias> =
        _checkInputsLiveData

    private val _loginTryLiveData = MutableLiveData<loginTryAlias>()
    val loginTryLiveData: LiveData<loginTryAlias> =
        _loginTryLiveData

    fun tryLogin(user: UserModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loginUtils.tryLogin(user)
            withContext(Dispatchers.Main) {
                _loginTryLiveData.value = result
            }
        }
    }

    fun checkInputs(login: String, password: String) {
        _checkInputsLiveData.value = checkLoginInputsUseCase.invoke(
            login = login,
            password = password
        )
    }
}

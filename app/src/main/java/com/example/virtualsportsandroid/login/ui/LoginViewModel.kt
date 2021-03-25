package com.example.virtualsportsandroid.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.login.data.model.AccessTokenResponse
import com.example.virtualsportsandroid.login.data.model.UserModel
import com.example.virtualsportsandroid.login.domain.*
import com.example.virtualsportsandroid.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private typealias CheckLoginInputsResult = Result<Boolean, LoginInputsError>
private typealias LoginTryResult = Result<AccessTokenResponse, LoginErrorType>

class LoginViewModel @Inject constructor(
    private val checkLoginInputsUseCase: CheckLoginInputsUseCase,
    private val loginUseCase: LoginUseCase,
    private val networkErrorMapper: NetworkToLoginErrorsMapper
) : ViewModel() {

    private val _checkInputsLiveData = MutableLiveData<CheckLoginInputsResult>()
    val checkInputsLiveData: LiveData<CheckLoginInputsResult> =
        _checkInputsLiveData

    private val _loginTryLiveData = MutableLiveData<LoginTryResult>()
    val loginTryLiveData: LiveData<LoginTryResult> =
        _loginTryLiveData

    fun checkInputs(user: UserModel) {
        _checkInputsLiveData.value = checkLoginInputsUseCase(user)
    }

    fun tryLogin(user: UserModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loginUseCase(user).mapError {
                networkErrorMapper.invoke(it)
            }
            withContext(Dispatchers.Main) {
                _loginTryLiveData.value = result
            }
        }
    }
}

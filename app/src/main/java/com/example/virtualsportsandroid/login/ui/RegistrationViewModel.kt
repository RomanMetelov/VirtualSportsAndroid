package com.example.virtualsportsandroid.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.login.data.api.RegistrationErrorType
import com.example.virtualsportsandroid.login.data.api.RegistrationUtils
import com.example.virtualsportsandroid.login.data.model.AccessTokenResponse
import com.example.virtualsportsandroid.login.data.model.UserModel
import com.example.virtualsportsandroid.login.domain.CheckRegistrationInputsUseCase
import com.example.virtualsportsandroid.login.domain.NetworkToRegisterErrorsMapper
import com.example.virtualsportsandroid.login.domain.RegistrationInputsError
import com.example.virtualsportsandroid.login.domain.RegistrationUserInputs
import com.example.virtualsportsandroid.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private typealias checkRegInputsAlias = Result<Boolean, RegistrationInputsError>
private typealias registrationTryAlias = Result<AccessTokenResponse, RegistrationErrorType>

class RegistrationViewModel
@Inject constructor(
    private var checkRegistrationInputsUseCase: CheckRegistrationInputsUseCase,
    private val registrationUtils: RegistrationUtils,
    private val networkErrorMapper: NetworkToRegisterErrorsMapper
) : ViewModel() {

    private val _checkInputsLiveData = MutableLiveData<checkRegInputsAlias>()
    val checkInputsLiveData: LiveData<Result<Boolean, RegistrationInputsError>> =
        _checkInputsLiveData

    private val _registrationTryLiveData = MutableLiveData<registrationTryAlias>()
    val registrationTryLiveData: LiveData<registrationTryAlias> =
        _registrationTryLiveData


    fun checkInputs(userInputs: RegistrationUserInputs) {
        _checkInputsLiveData.value = checkRegistrationInputsUseCase.invoke(userInputs)
    }

    fun tryRegister(user: UserModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = registrationUtils.tryRegister(user).mapError {
                networkErrorMapper.invoke(it)
            }
            withContext(Dispatchers.Main) {
                _registrationTryLiveData.value = result
            }
        }
    }
}

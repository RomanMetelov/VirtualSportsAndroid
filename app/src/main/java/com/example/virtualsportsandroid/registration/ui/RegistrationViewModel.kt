package com.example.virtualsportsandroid.registration.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualsportsandroid.login.data.model.AccessTokenResponse
import com.example.virtualsportsandroid.login.data.model.UserModel
import com.example.virtualsportsandroid.registration.domain.*
import com.example.virtualsportsandroid.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private typealias CheckRegInputsResult = Result<Boolean, RegistrationInputsError>
private typealias RegistrationTryResult = Result<AccessTokenResponse, RegistrationErrorType>

class RegistrationViewModel
@Inject constructor(
    private var checkRegistrationInputsUseCase: CheckRegistrationInputsUseCase,
    private val registrationUseCase: RegistrationUseCase,
    private val networkErrorMapper: NetworkToRegisterErrorsMapper
) : ViewModel() {

    private val _checkInputsLiveData = MutableLiveData<CheckRegInputsResult>()
    val checkInputsLiveData: LiveData<Result<Boolean, RegistrationInputsError>> =
        _checkInputsLiveData

    private val _registrationTryLiveData = MutableLiveData<RegistrationTryResult>()
    val registrationTryLiveData: LiveData<RegistrationTryResult> =
        _registrationTryLiveData

    fun checkInputs(userInputs: RegistrationUserInputs) {
        _checkInputsLiveData.value = checkRegistrationInputsUseCase.invoke(userInputs)
    }

    fun tryRegister(user: UserModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = registrationUseCase(user).mapError {
                networkErrorMapper.invoke(it)
            }
            withContext(Dispatchers.Main) {
                _registrationTryLiveData.value = result
            }
        }
    }
}

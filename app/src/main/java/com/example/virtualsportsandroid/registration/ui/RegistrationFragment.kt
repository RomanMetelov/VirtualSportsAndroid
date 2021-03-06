@file:Suppress("TooManyFunctions")

package com.example.virtualsportsandroid.registration.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.virtualsportsandroid.Application
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.RegistrationFragmentBinding
import com.example.virtualsportsandroid.login.data.model.AccessTokenResponse
import com.example.virtualsportsandroid.login.data.model.UserModel
import com.example.virtualsportsandroid.registration.domain.RegistrationErrorType
import com.example.virtualsportsandroid.registration.domain.RegistrationInputsError
import com.example.virtualsportsandroid.registration.domain.RegistrationInputsErrorType
import com.example.virtualsportsandroid.registration.domain.RegistrationUserInputs
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import com.example.virtualsportsandroid.utils.ui.hide
import com.example.virtualsportsandroid.utils.ui.show
import com.example.virtualsportsandroid.utils.ui.showError
import javax.inject.Inject

class RegistrationFragment : BaseFragment(R.layout.login_fragment) {
    companion object {
        fun newInstance(): RegistrationFragment = RegistrationFragment()
    }

    private lateinit var binding: RegistrationFragmentBinding
    private lateinit var etMail: AppCompatEditText
    private lateinit var etPassword: AppCompatEditText
    private lateinit var etRepeatPassword: AppCompatEditText
    private lateinit var btnRegister: AppCompatButton
    private lateinit var btnClose: AppCompatImageView
    private lateinit var btnLogin: AppCompatButton
    private lateinit var tvRegistrationErrorMessage: TextView

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RegistrationViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupDi()
        binding = RegistrationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        viewModel = ViewModelProvider(this, viewModelFactory).get(RegistrationViewModel::class.java)
        setupListeners()
        observeCheckInputsLiveData()
        observeRegistrationTryLiveData()
    }

    private fun setupViews() {
        etMail = binding.etMail
        etPassword = binding.etPassword
        etRepeatPassword = binding.etRepeatPassword
        btnRegister = binding.btnRegister
        btnClose = binding.btnClose
        btnLogin = binding.btnLogin
        tvRegistrationErrorMessage = binding.tvRegistrationErrorMessage
    }

    private fun setupListeners() {
        etMail.doAfterTextChanged {
            checkAllRules()
            tvRegistrationErrorMessage.hide()
        }
        etPassword.doAfterTextChanged { checkAllRules() }
        etRepeatPassword.doAfterTextChanged { checkAllRules() }
        btnRegister.setOnClickListener {
            tvRegistrationErrorMessage.hide()
            tryRegister()
        }
        btnClose.setOnClickListener { navigator.back() }
        btnLogin.setOnClickListener { navigator.showLoginFragment() }
    }

    private fun checkAllRules() {
        val login = etMail.text.toString()
        val password = etPassword.text.toString()
        val repeatPassword = etRepeatPassword.text.toString()

        viewModel.checkInputs(
            RegistrationUserInputs(
                mail = login,
                password = password,
                repeatPassword = repeatPassword
            )
        )
    }

    private fun observeCheckInputsLiveData() {
        viewModel.checkInputsLiveData.observe(viewLifecycleOwner, { result ->
            if (result.isError) {
                handleInputsError(result.errorResult)
                disableRegisterButton()
            } else {
                enableRegisterButton()
            }
        })
    }

    private fun handleInputsError(inputsErrorResult: RegistrationInputsError) {
        when (inputsErrorResult.type) {
            RegistrationInputsErrorType.MIN_MAIL_LENGTH, RegistrationInputsErrorType.MAX_MAIL_LENGTH,
            RegistrationInputsErrorType.INCORRECT_MAIL -> {
                showErrorOnEditText(
                    etMail,
                    inputsErrorResult.type.messageError,
                    inputsErrorResult.requireValue
                )
            }
            RegistrationInputsErrorType.MIN_PASSWORD_LENGTH, RegistrationInputsErrorType.MAX_PASSWORD_LENGTH -> {
                showErrorOnEditText(
                    etPassword,
                    inputsErrorResult.type.messageError,
                    inputsErrorResult.requireValue
                )
            }
            RegistrationInputsErrorType.NOT_SAME_PASSWORD -> {
                showErrorOnEditText(
                    etRepeatPassword,
                    inputsErrorResult.type.messageError,
                    inputsErrorResult.requireValue
                )
            }
        }
    }

    private fun showErrorOnEditText(
        editText: AppCompatEditText,
        @StringRes idResErrorText: Int,
        requireValue: String
    ) {
        editText.showError(requireActivity().getString(idResErrorText, requireValue))
    }

    private fun observeRegistrationTryLiveData() {
        viewModel.registrationTryLiveData.observe(viewLifecycleOwner, { result ->
            if (result.isError) {
                handleRegisterError(result.errorResult)
            } else {
                saveUserTokenToLocal(result.successResult)
                navigator.showMainFragment()
            }
        })
    }

    private fun handleRegisterError(errorResult: RegistrationErrorType) {
        with(binding.tvRegistrationErrorMessage) {
            when (errorResult) {
                RegistrationErrorType.NETWORK_ERROR -> navigator.showNoNetworkFragment()
                RegistrationErrorType.INPUTS_ERROR, RegistrationErrorType.USER_EXIST -> {
                    this.show()
                    this.text = getString(errorResult.errorMessage)
                }
                else -> return
            }
        }
    }

    private fun saveUserTokenToLocal(successResult: AccessTokenResponse) {
        sharedPreferences.token = successResult.accessToken
    }

    private fun enableRegisterButton() {
        etMail.error = null
        etPassword.error = null
        etRepeatPassword.error = null
        btnRegister.isEnabled = true
        btnRegister.setBackgroundColor(requireActivity().getColor(R.color.bright_yellow_13))
        btnRegister.setTextColor(requireActivity().getColor(R.color.black))
    }

    private fun disableRegisterButton() {
        btnRegister.isEnabled = false
        btnRegister.setBackgroundColor(requireActivity().getColor(R.color.gray_light_eb))
        btnRegister.setTextColor(requireActivity().getColor(R.color.gray_light_9e))
    }

    private fun setupDi() {
        val app = requireActivity().application as Application
        app.getComponent().inject(this)
    }

    private fun tryRegister() {
        viewModel.tryRegister(
            UserModel(
                mail = etMail.text.toString(),
                password = etPassword.text.toString()
            )
        )
    }
}

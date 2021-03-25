@file:Suppress("TooManyFunctions")

package com.example.virtualsportsandroid.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.doAfterTextChanged
import com.example.virtualsportsandroid.Application
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.LoginFragmentBinding
import com.example.virtualsportsandroid.login.data.api.LoginErrorType
import com.example.virtualsportsandroid.login.data.model.AccessTokenResponse
import com.example.virtualsportsandroid.login.data.model.UserModel
import com.example.virtualsportsandroid.login.domain.LoginInputsError
import com.example.virtualsportsandroid.login.domain.LoginInputsErrorType
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import com.example.virtualsportsandroid.utils.ui.show
import com.example.virtualsportsandroid.utils.ui.showError
import javax.inject.Inject

class LoginFragment : BaseFragment(R.layout.login_fragment) {
    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }

    private lateinit var binding: LoginFragmentBinding
    private lateinit var etLogin: AppCompatEditText
    private lateinit var etPassword: AppCompatEditText
    private lateinit var btnLogin: AppCompatButton
    private lateinit var btnRegister: AppCompatButton
    private lateinit var btnClose: AppCompatImageView

    @Inject
    lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupDi()
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //checkSavedToken()
        setupViews()
        setupListeners()
        observeCheckInputsLiveData()
        observeLoginTryLiveData()
    }

    //private fun checkSavedToken() {
    //if (sharedPreferences.token.isNotEmpty())
    //navigator.back()
    //}

    private fun setupViews() {
        btnLogin = binding.btnLogin
        btnRegister = binding.btnRegister
        btnClose = binding.btnClose
        etLogin = binding.etLogin
        etPassword = binding.etPassword
    }

    private fun setupListeners() {
        btnLogin.setOnClickListener {
            tryLogin()
        }
        btnClose.setOnClickListener {
            closeScreen()
        }
        btnRegister.setOnClickListener {
            navigator.showRegistrationFragment()
        }
        etLogin.doAfterTextChanged {
            checkAllRules()
        }
        etPassword.doAfterTextChanged {
            checkAllRules()
        }
    }

    private fun checkAllRules() {
        val login = etLogin.text.toString()
        val password = etPassword.text.toString()

        viewModel.checkInputs(
            UserModel(
                login = login,
                password = password
            )
        )
    }

    private fun observeCheckInputsLiveData() {
        viewModel.checkInputsLiveData.observe(viewLifecycleOwner, { result ->
            if (result.isError) {
                handleInputsError(result.errorResult)
                disableLoginButton()
            } else {
                enableLoginButton()
            }
        })
    }

    private fun handleInputsError(inputsErrorResult: LoginInputsError) {
        when (inputsErrorResult.type) {
            LoginInputsErrorType.EMPTY_LOGIN -> {
                showErrorOnEditText(
                    etLogin,
                    inputsErrorResult.type.messageError,
                    inputsErrorResult.requireValue
                )
            }
            LoginInputsErrorType.EMPTY_PASSWORD -> {
                showErrorOnEditText(
                    etPassword,
                    inputsErrorResult.type.messageError,
                    inputsErrorResult.requireValue
                )
            }
        }
    }

    private fun observeLoginTryLiveData() {
        viewModel.loginTryLiveData.observe(viewLifecycleOwner, { result ->
            if (result.isError) {
                handleLoginError(result.errorResult)
            } else {
                saveUserTokenToLocal(result.successResult)
                requireActivity().onBackPressed()
            }
        })
    }

    private fun handleLoginError(errorResult: LoginErrorType) {
        with(binding.tvLoginErrorMessage) {
            when (errorResult) {
                LoginErrorType.NETWORK_ERROR -> navigator.showNoNetworkFragment()
                LoginErrorType.LOGIN_ERROR, LoginErrorType.INPUTS_ERROR -> {
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

    private fun showErrorOnEditText(
        editText: AppCompatEditText,
        @StringRes idResErrorText: Int,
        requireValue: String
    ) {
        editText.showError(requireActivity().getString(idResErrorText, requireValue))
    }

    private fun tryLogin() {
        viewModel.tryLogin(
            UserModel(
                login = etLogin.text.toString(),
                password = etPassword.text.toString()
            )
        )
    }

    private fun closeScreen() {
        navigator.back()
    }

    private fun enableLoginButton() {
        etLogin.error = null
        etPassword.error = null
        btnLogin.isEnabled = true
        btnLogin.setBackgroundColor(requireActivity().getColor(R.color.bright_yellow_13))
        btnLogin.setTextColor(requireActivity().getColor(R.color.black))
    }

    private fun disableLoginButton() {
        btnLogin.isEnabled = false
        btnLogin.setBackgroundColor(requireActivity().getColor(R.color.gray_light_eb))
        btnLogin.setTextColor(requireActivity().getColor(R.color.gray_light_9e))
    }

    private fun setupDi() {
        val app = requireActivity().application as Application
        app.getComponent().inject(this)
    }
}

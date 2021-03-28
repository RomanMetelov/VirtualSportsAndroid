@file:Suppress("TooManyFunctions")

package com.example.virtualsportsandroid.login.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.example.virtualsportsandroid.login.data.model.AccessTokenResponse
import com.example.virtualsportsandroid.login.data.model.UserModel
import com.example.virtualsportsandroid.login.domain.LoginErrorType
import com.example.virtualsportsandroid.login.domain.LoginInputsError
import com.example.virtualsportsandroid.login.domain.LoginInputsErrorType
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import com.example.virtualsportsandroid.utils.ui.show
import com.example.virtualsportsandroid.utils.ui.showError
import javax.inject.Inject

private const val LOG_TAG = "QZLOGIN_FRAGMENT"

class LoginFragment : BaseFragment(R.layout.login_fragment) {
    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }

    private lateinit var binding: LoginFragmentBinding
    private lateinit var etMail: AppCompatEditText
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
        Log.d(LOG_TAG, "onCreateView")
        setupDi()
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
        observeCheckInputsLiveData()
        observeLoginTryLiveData()
    }

    private fun setupViews() {
        btnLogin = binding.btnLogin
        btnRegister = binding.btnRegister
        btnClose = binding.btnClose
        etMail = binding.etMail
        etPassword = binding.etPassword
    }

    private fun setupListeners() {
        btnLogin.setOnClickListener { tryLogin() }
        btnClose.setOnClickListener { navigator.back() }
        btnRegister.setOnClickListener { navigator.showRegistrationFragment() }
        etMail.doAfterTextChanged { checkAllRules() }
        etPassword.doAfterTextChanged { checkAllRules() }
    }

    private fun checkAllRules() {
        val mail = etMail.text.toString()
        val password = etPassword.text.toString()

        viewModel.checkInputs(
            UserModel(
                mail = mail,
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
            LoginInputsErrorType.EMPTY_MAIL, LoginInputsErrorType.INCORRECT_MAIL -> {
                showErrorOnEditText(
                    etMail,
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
        Log.d("QQQQQQQQQQQQQQ", sharedPreferences.token)
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
                mail = etMail.text.toString(),
                password = etPassword.text.toString()
            )
        )
    }

    private fun enableLoginButton() {
        etMail.error = null
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

    override fun onAttach(context: Context) {
        Log.d(LOG_TAG, "onAttach")
        super.onAttach(context)
    }

    override fun onDestroy() {
        Log.d(LOG_TAG, "onDestroy")
        super.onDestroy()

    }

    override fun onDestroyView() {
        Log.d(LOG_TAG, "onDestroyView")
        super.onDestroyView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onDetach() {
        Log.d(LOG_TAG, "onDetach")
        super.onDetach()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Log.d(LOG_TAG, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(LOG_TAG, "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d(LOG_TAG, "onPause")
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(LOG_TAG, "onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        Log.d(LOG_TAG, "onStop")
        super.onStop()
    }

}

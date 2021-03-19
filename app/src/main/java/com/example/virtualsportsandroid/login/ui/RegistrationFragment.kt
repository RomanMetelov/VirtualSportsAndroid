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
import com.example.virtualsportsandroid.databinding.RegistrationFragmentBinding
import com.example.virtualsportsandroid.login.domain.RegistrationError
import com.example.virtualsportsandroid.login.domain.RegistrationErrorTypeText
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import com.example.virtualsportsandroid.utils.ui.showError
import javax.inject.Inject

class RegistrationFragment : BaseFragment(R.layout.login_fragment) {
    companion object {
        fun newInstance(): RegistrationFragment = RegistrationFragment()
    }

    private lateinit var binding: RegistrationFragmentBinding
    private lateinit var etLogin: AppCompatEditText
    private lateinit var etPassword: AppCompatEditText
    private lateinit var etRepeatPassword: AppCompatEditText
    private lateinit var btnRegister: AppCompatButton
    private lateinit var btnClose: AppCompatImageView

    @Inject
    lateinit var viewModel: RegistrationViewModel


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
        setupListeners()
        observeCheckInputsLiveData()
    }

    private fun setupViews() {
        etLogin = binding.etLogin
        etPassword = binding.etPassword
        etRepeatPassword = binding.etRepeatPassword
        btnRegister = binding.btnRegister
        btnClose = binding.btnClose
    }

    private fun setupListeners() {
        etLogin.doAfterTextChanged {
            checkAllRules()
        }
        etPassword.doAfterTextChanged {
            checkAllRules()
        }
        etRepeatPassword.doAfterTextChanged {
            checkAllRules()
        }
        btnRegister.setOnClickListener {
            tryRegister()
        }
        btnClose.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun checkAllRules() {
        val login = etLogin.text.toString()
        val password = etPassword.text.toString()
        val repeatPassword = etRepeatPassword.text.toString()

        viewModel.checkInputs(
            login = login,
            password = password,
            repeatPassword = repeatPassword
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

    private fun handleInputsError(errorResult: RegistrationError) {
        when (errorResult.type) {
            RegistrationErrorTypeText.MIN_LOGIN_LENGTH, RegistrationErrorTypeText.MAX_LOGIN_LENGTH -> {
                showErrorOnEditText(
                    etLogin,
                    errorResult.type.messageError,
                    errorResult.requireValue
                )
            }
            RegistrationErrorTypeText.MIN_PASSWORD_LENGTH, RegistrationErrorTypeText.MAX_PASSWORD_LENGTH -> {
                showErrorOnEditText(
                    etPassword,
                    errorResult.type.messageError,
                    errorResult.requireValue
                )
            }
            RegistrationErrorTypeText.NOT_SAME_PASSWORD -> {
                showErrorOnEditText(
                    etRepeatPassword,
                    errorResult.type.messageError,
                    errorResult.requireValue
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

    private fun enableRegisterButton() {
        etLogin.error = null
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
        requireActivity().onBackPressed()
    }
}

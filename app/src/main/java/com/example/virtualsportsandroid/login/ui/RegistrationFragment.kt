@file:Suppress("TooManyFunctions")

package com.example.virtualsportsandroid.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.doAfterTextChanged
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.RegistrationFragmentBinding
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import com.example.virtualsportsandroid.utils.ui.showError

class RegistrationFragment : BaseFragment(R.layout.login_fragment) {

    companion object {
        fun newInstance(): RegistrationFragment = RegistrationFragment()
        private const val minLoginLength = 3
        private const val maxLoginLength = 15
        private const val minPasswordLength = 8
        private const val maxPasswordLength = 20
    }

    private lateinit var binding: RegistrationFragmentBinding
    private lateinit var etLogin: AppCompatEditText
    private lateinit var etPassword: AppCompatEditText
    private lateinit var etRepeatPassword: AppCompatEditText
    private lateinit var btnRegister: AppCompatButton
    private lateinit var btnClose: AppCompatImageView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RegistrationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()

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
        if (checkLoginLength(login) && checkPasswordRules(password, repeatPassword)) {
            enableRegisterButton()
        } else {
            disableRegisterButton()
        }
    }

    private fun checkLoginLength(login: String): Boolean {
        return when {
            login.length < minLoginLength -> {
                etLogin.showError(
                    requireActivity().getString(
                        R.string.min_login_length_error, minLoginLength.toString()
                    )
                )
                false
            }
            login.length > maxLoginLength -> {
                etLogin.showError(
                    requireActivity().getString(
                        R.string.max_login_length_error, maxLoginLength.toString()
                    )
                )
                false
            }
            else -> true
        }
    }

    private fun checkPasswordRules(password: String, repeatPassword: String): Boolean {
        return if (password.isNotEmpty()) {
            checkPasswordLength(password) && checkSamePassword(password, repeatPassword)
        } else {
            false
        }
    }

    private fun checkPasswordLength(password: String): Boolean {
        return when {
            password.length < minPasswordLength -> {
                etPassword.showError(
                    requireActivity().getString(
                        R.string.min_password_length_error,
                        minPasswordLength.toString()
                    )
                )
                false
            }
            password.length > maxPasswordLength -> {
                etPassword.showError(
                    requireActivity().getString(
                        R.string.max_password_length_error,
                        minPasswordLength.toString()
                    )
                )
                false
            }
            else -> true
        }
    }

    private fun checkSamePassword(password: String, repeatPassword: String): Boolean {
        return if (password != repeatPassword) {
            etRepeatPassword.showError(
                requireActivity().getString(R.string.not_same_password_error)
            )
            false
        } else {
            true
        }
    }


    private fun enableRegisterButton() {
        btnRegister.isEnabled = true
        btnRegister.setBackgroundColor(requireActivity().getColor(R.color.bright_yellow_13))
        btnRegister.setTextColor(requireActivity().getColor(R.color.black))
    }

    private fun disableRegisterButton() {
        btnRegister.isEnabled = false
        btnRegister.setBackgroundColor(requireActivity().getColor(R.color.gray_light_eb))
        btnRegister.setTextColor(requireActivity().getColor(R.color.gray_light_9e))
    }

    private fun tryRegister() {
        requireActivity().onBackPressed()
    }

}

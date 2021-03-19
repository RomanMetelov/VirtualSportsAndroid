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
import com.example.virtualsportsandroid.databinding.LoginFragmentBinding
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import com.example.virtualsportsandroid.utils.ui.showError

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
    }

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
        if (checkLoginLength(login) && checkPasswordLength(password)) {
            enableLoginButton()
        } else {
            disableLoginButton()
        }
    }


    private fun checkLoginLength(login: String): Boolean {
        return if (login.isEmpty()) {
            etLogin.showError(requireActivity().getString(R.string.empty_login_error))
            false
        } else true
    }

    private fun checkPasswordLength(password: String): Boolean {
        return password.isNotEmpty()
    }

    private fun tryLogin() {
        closeScreen()
    }

    private fun closeScreen() {
        requireActivity().onBackPressed()
    }

    private fun enableLoginButton() {
        btnLogin.isEnabled = true
        btnLogin.setBackgroundColor(requireActivity().getColor(R.color.bright_yellow_13))
        btnLogin.setTextColor(requireActivity().getColor(R.color.black))
    }

    private fun disableLoginButton() {
        btnLogin.isEnabled = false
        btnLogin.setBackgroundColor(requireActivity().getColor(R.color.gray_light_eb))
        btnLogin.setTextColor(requireActivity().getColor(R.color.gray_light_9e))
    }
}

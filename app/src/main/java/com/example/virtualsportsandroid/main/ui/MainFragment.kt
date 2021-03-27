@file:Suppress("TooManyFunctions")
package com.example.virtualsportsandroid.main.ui

import android.os.Bundle
import android.view.View
import com.example.virtualsportsandroid.Application
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.MainFragmentBinding
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import com.example.virtualsportsandroid.utils.ui.hide
import com.example.virtualsportsandroid.utils.ui.show
import javax.inject.Inject

class MainFragment : BaseFragment(R.layout.main_fragment) {

    @Inject
    lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private val mainFragmentNavigator: MainFragmentNavigator by lazy {
        MainFragmentNavigator(
            childFragmentManager,
            R.id.fragmentContainer,
            { viewModel.showFilterFragment() },
            { category, providers -> viewModel.showGamesFragment(category, providers) }
        )
    }


    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)
        (requireActivity().application as Application).getComponent().inject(this)
        observeMainFragmentState()
        observeIsAuthorized()
        observeContainerState()
        observeLogoutResult()
        observeLoadGamesResult()
        setupListeners()
        viewModel.loadConfigs()
        viewModel.checkIsAuthorized()
    }

    private fun setupListeners() {
        with(binding.loginHeader) {
            btnLogin.setOnClickListener {
                navigator.showLoginFragment()
            }
            btnSignUp.setOnClickListener {
                navigator.showRegistrationFragment()
            }
            btnLogout.setOnClickListener {
                viewModel.logout {
                    navigator.showMainFragment()
                }
            }
        }
    }

    private fun observeMainFragmentState() {
        viewModel.mainFragmentStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                MainFragmentState.Loading -> showLoading()
                MainFragmentState.Content -> showContent()
                is MainFragmentState.Error -> showError(it.errorMessage)
            }
        }
    }

    private fun showError(errorMessage: String) {
        with(binding) {
            loginHeader.headerContainer.hide()
            fragmentContainer.hide()
            pbLoading.hide()
            tvErrorMessage.show()
            tvErrorMessage.text = errorMessage
        }
    }

    private fun showLoading() {
        with(binding) {
            loginHeader.headerContainer.hide()
            fragmentContainer.hide()
            tvErrorMessage.hide()
            pbLoading.show()
        }
    }

    private fun showContent() {
        with(binding) {
            pbLoading.hide()
            tvErrorMessage.hide()
            loginHeader.headerContainer.show()
            fragmentContainer.show()
        }
    }

    private fun observeContainerState() {
        viewModel.containerStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is MainFragmentContainerState.Filter -> mainFragmentNavigator.showFilterFragment()
                is MainFragmentContainerState.Games -> mainFragmentNavigator.showGamesFragment(
                    it.category,
                    it.providers
                )
            }
        }
    }

    private fun observeIsAuthorized() {
        viewModel.isAuthorizedLiveData.observe(viewLifecycleOwner) {
            with(binding.loginHeader) {
                if (it) {
                    btnLogout.show()
                    gButtonsForUnauthorizedUser.hide()
                } else {
                    btnLogout.hide()
                    gButtonsForUnauthorizedUser.show()
                }
            }
        }
    }

    private fun observeLogoutResult() {
        viewModel.logoutResultLiveData.observe(viewLifecycleOwner, { result ->
            if (result.isError) {
                handleNetworkError(result.errorResult)
            }
        })
    }

    private fun observeLoadGamesResult() {
        viewModel.loadGamesResult.observe(viewLifecycleOwner, { result ->
            if (result.isError) {
                handleNetworkError(result.errorResult)
            }
        })
    }

    private fun handleNetworkError(errorType: NetworkErrorType) {
        when (errorType) {
            NetworkErrorType.NO_NETWORK -> navigator.showNoNetworkFragment()
            else -> viewModel.showError(getString(R.string.unknown_error_text))
        }
    }

}

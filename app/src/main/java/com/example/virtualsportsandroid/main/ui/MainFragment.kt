@file:Suppress("TooManyFunctions")
package com.example.virtualsportsandroid.main.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.virtualsportsandroid.Application
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.DiceGameFragmentBinding
import com.example.virtualsportsandroid.databinding.MainFragmentBinding
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import com.example.virtualsportsandroid.utils.ui.hide
import com.example.virtualsportsandroid.utils.ui.show
import javax.inject.Inject

private const val LOG_TAG = "QZMAIN_FRAGMENT"

class MainFragment : BaseFragment(R.layout.main_fragment) {
    //val LOG_TAG = "myLogs"
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
        Log.d(LOG_TAG, "onViewCreated")
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
                is MainFragmentState.Error -> showError()
            }
        }
    }

    private fun showError() {
        with(binding) {
            loginHeader.headerContainer.hide()
            fragmentContainer.hide()
            pbLoading.hide()
            ivError.show()
        }
    }

    private fun showLoading() {
        with(binding) {
            loginHeader.headerContainer.hide()
            fragmentContainer.hide()
            ivError.hide()
            pbLoading.show()
        }
    }

    private fun showContent() {
        with(binding) {
            pbLoading.hide()
            ivError.hide()
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
            else -> viewModel.showError()
        }
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(LOG_TAG, "onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStop() {
        Log.d(LOG_TAG, "onStop")
        super.onStop()
    }

}

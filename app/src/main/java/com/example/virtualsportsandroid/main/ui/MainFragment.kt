package com.example.virtualsportsandroid.main.ui

import android.os.Bundle
import android.view.View
import com.example.virtualsportsandroid.Application
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.MainFragmentBinding
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
            { category, providers ->
                viewModel.showGamesFragment(category, providers)
            })
    }


    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)
        (requireActivity().application as Application).getComponent().inject(this)
        observeIsAuthorized()
        setupListeners()
        viewModel.checkIsAuthorized()
        observeContainerState()
        viewModel.showGamesFragment(null, null)
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
                //implementation
            }
            ivDiceGameLaunch.setOnClickListener {
                navigator.showDiceGameFragment()
            }

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
                    btnLogin.hide()
                    btnSignUp.hide()
                } else {
                    btnLogout.hide()
                    btnLogin.show()
                    btnSignUp.show()
                }
            }
        }
    }
}

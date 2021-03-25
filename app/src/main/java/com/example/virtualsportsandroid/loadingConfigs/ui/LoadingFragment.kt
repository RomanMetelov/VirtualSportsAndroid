package com.example.virtualsportsandroid.loadingConfigs.ui

import android.os.Bundle
import android.view.View
import com.example.virtualsportsandroid.Application
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.game.data.ScreenGameModel
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import javax.inject.Inject

class LoadingFragment : BaseFragment(R.layout.loading_fragment) {

    companion object {
        fun newInstance() = LoadingFragment()
    }

    @Inject
    lateinit var viewModel: LoadingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        viewModel.loadConfigs()
    }

    private fun setupViewModel() {
        (requireActivity().application as Application).getComponent().inject(this)
        observeFragmentState()
    }

    private fun observeFragmentState() {
        viewModel.loadingStateLiveData.observe(viewLifecycleOwner) {
            if (it == ConfigsLoadingState.Successful) {
                //navigator.showMainFragment()
                //navigator.showLoginFragment()
                navigator.showGameFragment(
                    ScreenGameModel(
                        "",
                        "Game test",
                        "https://vsw.betradar.com/ls/mobile/?/parimatchrgs/ru/page/vsmobile_demo"
                    )
                )
            }
        }
    }
}

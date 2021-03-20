package com.example.virtualsportsandroid.mainScreen.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualsportsandroid.Application
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.MainFragmentBinding
import com.example.virtualsportsandroid.mainScreen.domain.model.GameModel
import com.example.virtualsportsandroid.mainScreen.ui.model.MainFragmentState
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import com.example.virtualsportsandroid.utils.ui.hide
import com.example.virtualsportsandroid.utils.ui.show
import javax.inject.Inject

class MainFragment private constructor() : BaseFragment(R.layout.main_fragment) {

    companion object {
        private const val ALL_GAMES_LIST_COLUMNS_NUMBER = 2

        fun newInstance() = MainFragment()
    }

    private lateinit var binding: MainFragmentBinding
    private val topGamesListAdapter = GamesListAdapter()
    private val allGamesListAdapter = GamesListAdapter()

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)
        setupRecyclerViews()
        setupViewModel()
        viewModel.loadNotFilteredGames()
    }

    private fun setupRecyclerViews() {
        with(binding) {
            rvTopGames.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvTopGames.addItemDecoration(
                GamesItemDecorator(
                    resources.getDimension(R.dimen.top_games_space_between).toInt()
                )
            )
            rvTopGames.adapter = topGamesListAdapter
            rvAllGames.layoutManager = GridLayoutManager(context, ALL_GAMES_LIST_COLUMNS_NUMBER)
            rvAllGames.adapter = allGamesListAdapter
        }
    }

    private fun setupViewModel() {
        (requireActivity().application as Application).getComponent().inject(this)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.mainFragmentStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is MainFragmentState.Loading -> showLoading()
                is MainFragmentState.NotFiltered -> showNotFilteredGames(it.topGames, it.allGames)
                is MainFragmentState.Error -> showError(it.errorMessage)
            }
        }
    }

    private fun showError(errorMessage: String) {
        with(binding) {
            header.headerContainer.hide()
            svContentContainer.hide()
            pbLoading.hide()
            tvErrorMessage.show()
            tvErrorMessage.text = errorMessage
        }
    }

    private fun showLoading() {
        with(binding) {
            header.headerContainer.hide()
            svContentContainer.hide()
            tvErrorMessage.hide()
            pbLoading.show()
        }
    }

    private fun showNotFilteredGames(topGames: List<GameModel>, allGames: List<GameModel>) {
        with(binding) {
            header.headerContainer.show()
            svContentContainer.show()
            tvTopGamesTitle.show()
            rvTopGames.show()
            tvAllGamesTitle.show()
            rvAllGames.show()
            pbLoading.hide()
            tvErrorMessage.hide()
            topGamesListAdapter.submitList(topGames)
            allGamesListAdapter.submitList(allGames)
        }
    }
}
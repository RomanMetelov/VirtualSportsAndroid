package com.example.virtualsportsandroid.games.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualsportsandroid.Application
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.GamesFragmentBinding
import com.example.virtualsportsandroid.games.domain.model.GamesList
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import com.example.virtualsportsandroid.utils.ui.hide
import com.example.virtualsportsandroid.utils.ui.show
import kotlinx.coroutines.launch
import javax.inject.Inject

class GamesFragment : BaseFragment(R.layout.games_fragment) {

    companion object {
        private const val CATEGORY_KEY = "CATEGORY_KEY"
        private const val PROVIDERS_KEY = "PROVIDERS_KEY"
        private const val DICE_GAME_ID = "dice"

        fun newInstance(
            category: String? = null,
            providers: List<String>? = null,
            showFilterFragment: () -> Unit
        ) =
            GamesFragment().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_KEY, category)
                    providers?.let {
                        putStringArray(PROVIDERS_KEY, it.toTypedArray())
                    }
                }
                this.showFilterFragment = showFilterFragment
            }
    }

    private lateinit var binding: GamesFragmentBinding
    private lateinit var showFilterFragment: () -> Unit

    @Inject
    lateinit var viewModel: GamesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = GamesFragmentBinding.bind(view)
        setupViewModel()
        observeMainFragmentState()
        loadData()
    }

    @Suppress("SpreadOperator")
    private fun loadData() {
        val category = arguments?.getString(CATEGORY_KEY)
        val providers = arguments?.getStringArray(PROVIDERS_KEY)
        if (category != null && providers != null) {
            viewModel.loadFilteredByCategoryAndProviders(category, listOf(*providers))
        } else if (category != null) {
            viewModel.loadFilteredByCategory(category)
        } else if (providers != null) {
            viewModel.loadFilteredByProviders(listOf(*providers))
        } else {
            viewModel.loadNotFilteredGames()
        }
    }

    private fun setupViewModel() {
        (requireActivity().application as Application).getComponent().inject(this)
    }

    private fun observeMainFragmentState() {
        viewModel.gamesFragmentStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is GamesFragmentState.Loading -> showLoading()
                is GamesFragmentState.NotFiltered -> showNotFilteredGames(
                    it.gamesWithFirstTag,
                    it.allGamesWithoutFirstTag
                )
                is GamesFragmentState.FilteredByCategory -> {
                    showFilteredGames(GamesList(it.categoryName, it.filteredGames))
                }
                is GamesFragmentState.FilteredByProviders -> {
                    showFilteredGames(
                        GamesList(
                            getString(R.string.search_results_title),
                            it.filteredGames
                        )
                    )
                }
                is GamesFragmentState.FilteredByProvidersAndCategory -> {
                    showFilteredGames(
                        GamesList(
                            getString(R.string.search_results_title),
                            it.filteredGames
                        )
                    )
                }
                is GamesFragmentState.Error -> showError(it.errorMessage)
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            rvMain.hide()
            tvErrorMessage.hide()
            pbLoading.show()
        }
    }


    private fun showNotFilteredGames(
        firstTagGames: GamesList,
        allGamesWithoutFirstTag: List<GamesList>
    ) {
        with(binding) {
            tvErrorMessage.hide()
            pbLoading.hide()
            with(rvMain) {
                show()
                layoutManager = LinearLayoutManager(context)
                adapter = MainRecyclerViewAdapter(
                    showFilterFragment,
                    firstTagGames,
                    allGamesWithoutFirstTag,
                    {
                        if (viewModel.isAuthorizeUser()) {
                            if (it == DICE_GAME_ID) {
                                navigator.showDiceGameFragment()
                            } else {
                                showLoading()
                                lifecycleScope.launch {
                                    val result = viewModel.loadScreenGameModel(it)
                                    if (result.isError) {
                                        showError(getString(R.string.unknown_error_text))
                                    } else {
                                        navigator.showGameFragment(result.successResult)
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                getString(R.string.need_login_error_message),
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.rvMain.show()
                            binding.pbLoading.hide()
                        }
                    }
                )
            }
        }
    }

    private fun showFilteredGames(gamesList: GamesList) {
        with(binding) {
            tvErrorMessage.hide()
            pbLoading.hide()
            with(rvMain) {
                show()
                layoutManager = LinearLayoutManager(context)
                adapter = MainRecyclerViewAdapter(
                    showFilterFragment,
                    null,
                    listOf(gamesList),
                    {
                        if (viewModel.isAuthorizeUser()) {
                            if (it == DICE_GAME_ID) {
                                navigator.showDiceGameFragment()
                            } else {
                                lifecycleScope.launch {
                                    showLoading()
                                    val result = viewModel.loadScreenGameModel(it)
                                    if (result.isError) {
                                        showError(getString(R.string.unknown_error_text))
                                    } else {
                                        navigator.showGameFragment(result.successResult)
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                getString(R.string.need_login_error_message),
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.rvMain.show()
                            binding.pbLoading.hide()
                        }
                    }
                )
            }
        }
    }

    private fun showError(errorMessage: String) {
        with(binding) {
            pbLoading.hide()
            rvMain.hide()
            with(tvErrorMessage) {
                text = errorMessage
                show()
            }
        }
    }
}

package com.example.virtualsportsandroid.games.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualsportsandroid.Application
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.GamesFragmentBinding
import com.example.virtualsportsandroid.games.domain.model.GameModel
import com.example.virtualsportsandroid.games.domain.model.TagModel
import com.example.virtualsportsandroid.main.ui.MainFragmentNavigator
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import com.example.virtualsportsandroid.utils.ui.hide
import com.example.virtualsportsandroid.utils.ui.show
import javax.inject.Inject

class GamesFragment : BaseFragment(R.layout.games_fragment) {

    companion object {
        private const val FILTERED_GAMES_LIST_COLUMNS_NUMBER = 2
        private const val CATEGORY_KEY = "CATEGORY_KEY"
        private const val PROVIDERS_KEY = "PROVIDERS_KEY"

        fun newInstance(
            category: String? = null,
            providers: List<String>? = null,
            mainFragmentNavigator: MainFragmentNavigator
        ) =
            GamesFragment().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_KEY, category)
                    providers?.let {
                        putStringArray(PROVIDERS_KEY, it.toTypedArray())
                    }
                }
                this.mainFragmentNavigator = mainFragmentNavigator
            }
    }

    private lateinit var binding: GamesFragmentBinding
    private lateinit var mainFragmentNavigator: MainFragmentNavigator

    private val firstTagGamesListAdapter = GamesListAdapter()
    private val allTagsWithoutFirstListAdapter = TagsListAdapter()
    private val filteredGamesListAdapter = GamesListAdapter()

    @Inject
    lateinit var viewModel: GamesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = GamesFragmentBinding.bind(view)
        setupViewModel()
        observeMainFragmentState()
        setupRecyclerViews()
        setupListeners()
        loadData()
    }

    private fun setupListeners() {
        with(binding) {
            llFilterButton.setOnClickListener {
                mainFragmentNavigator.showFilterFragment()
            }
        }
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

    private fun setupRecyclerViews() {
        with(binding) {
            rvFirstTagGames.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvFirstTagGames.addItemDecoration(
                GamesItemDecorator(
                    resources.getDimension(R.dimen.first_tag_games_space_between).toInt()
                )
            )
            rvFirstTagGames.adapter = firstTagGamesListAdapter
            rvTags.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvTags.adapter = allTagsWithoutFirstListAdapter
            rvFilteredGames.layoutManager =
                GridLayoutManager(context, FILTERED_GAMES_LIST_COLUMNS_NUMBER)
            rvFilteredGames.adapter = filteredGamesListAdapter
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
                    showFilteredGames(it.filteredGames)
                    binding.tvFilteredGamesTitle.text = it.categoryName
                }
                is GamesFragmentState.FilteredByProviders -> {
                    showFilteredGames(it.filteredGames)
                    binding.tvFilteredGamesTitle.text = getString(R.string.search_results_title)
                }
                is GamesFragmentState.FilteredByProvidersAndCategory -> {
                    showFilteredGames(it.filteredGames)
                    binding.tvFilteredGamesTitle.text = getString(R.string.search_results_title)
                }
                is GamesFragmentState.Error -> showError(it.errorMessage)
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            svContentContainer.hide()
            tvErrorMessage.hide()
            pbLoading.show()
        }
    }


    private fun showNotFilteredGames(
        firstTagGames: TagModel,
        allGamesWithoutFirstTag: List<TagModel>
    ) {
        with(binding) {
            svContentContainer.show()
            tvFirstTagName.apply {
                show()
                text = firstTagGames.name
            }
            rvFirstTagGames.show()
            firstTagGamesListAdapter.submitList(firstTagGames.games)
            rvTags.show()
            allTagsWithoutFirstListAdapter.submitList(allGamesWithoutFirstTag)
            tvFilteredGamesTitle.hide()
            rvFilteredGames.hide()
            tvErrorMessage.hide()
            pbLoading.hide()
        }
    }

    private fun showFilteredGames(filteredGames: List<GameModel>) {
        with(binding) {
            tvErrorMessage.hide()
            svContentContainer.show()
            tvFirstTagName.hide()
            rvFirstTagGames.hide()
            rvTags.hide()
            tvFilteredGamesTitle.show()
            rvFilteredGames.show()
            filteredGamesListAdapter.submitList(filteredGames)
            pbLoading.hide()
        }
    }

    private fun showError(errorMessage: String) {
        with(binding) {
            svContentContainer.hide()
            tvErrorMessage.apply {
                show()
                text = errorMessage
            }
            pbLoading.hide()
        }
    }
}

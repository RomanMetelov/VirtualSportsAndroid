package com.example.virtualsportsandroid.games.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualsportsandroid.Application
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.GamesFragmentBinding
import com.example.virtualsportsandroid.games.domain.model.GamesList
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import com.example.virtualsportsandroid.utils.ui.hide
import com.example.virtualsportsandroid.utils.ui.show
import com.google.android.material.transition.platform.MaterialFadeThrough
import kotlinx.android.synthetic.main.games_fragment.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("TooManyFunctions")
class GamesFragment : BaseFragment(R.layout.games_fragment) {

    companion object {
        private const val CATEGORY_KEY = "CATEGORY_KEY"
        private const val PROVIDERS_KEY = "PROVIDERS_KEY"
        private const val ANIMATE_LOADING = 200L

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
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: GamesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        exitTransition = MaterialFadeThrough()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = GamesFragmentBinding.inflate(layoutInflater)
        observeGamesFragmentState()
        observeLoadGamesResult()
        viewModel.loadDataFromServer()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivError.hide()
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

    @Suppress("SpreadOperator")
    private fun loadData() {
        val category = arguments?.getString(CATEGORY_KEY)
        val providers = arguments?.getStringArray(PROVIDERS_KEY)
        when {
            category != null && providers != null -> {
                viewModel.loadFilteredByCategoryAndProviders(category, listOf(*providers))
            }
            category != null -> viewModel.loadFilteredByCategory(category)
            providers != null -> viewModel.loadFilteredByProviders(listOf(*providers))
            else -> viewModel.loadNotFilteredGames()
        }
    }

    private fun setupViewModel() {
        (requireActivity().application as Application).getComponent().inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GamesViewModel::class.java)
    }

    private fun observeGamesFragmentState() {
        viewModel.gamesFragmentStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is GamesFragmentState.Loading -> showLoading()
                is GamesFragmentState.Content -> loadData()
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
                is GamesFragmentState.Error -> showError()
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            rvMain.hide()
            ivError.hide()
            vLoadingBackground.show()
            pbLoading.show()
        }
    }


    private fun showNotFilteredGames(
        firstTagGames: GamesList,
        allGamesWithoutFirstTag: List<GamesList>
    ) {
        with(binding) {
            ivError.hide()
            root.postDelayed({
                vLoadingBackground.hide()
                pbLoading.hide()
            }, ANIMATE_LOADING)
            with(rvMain) {
                show()
                layoutManager = LinearLayoutManager(context)
                adapter = MainRecyclerViewAdapter(
                    showFilterFragment,
                    firstTagGames,
                    allGamesWithoutFirstTag,
                    {
                        openGame(it)
                    }
                )
                itemAnimator = null
            }
        }
    }

    private fun showFilteredGames(gamesList: GamesList) {
        with(binding) {
            ivError.hide()
            root.postDelayed({
                vLoadingBackground.hide()
                pbLoading.hide()
            }, ANIMATE_LOADING)
            with(rvMain) {
                show()
                layoutManager = LinearLayoutManager(context)
                adapter = MainRecyclerViewAdapter(
                    showFilterFragment,
                    null,
                    listOf(gamesList),
                    {
                        openGame(it)
                    }
                )
            }
        }
    }

    private fun openGame(gameName: String) {
        if (viewModel.isAuthorizeUser()) {
            lifecycleScope.launch {
                showLoading()
                val result = viewModel.loadScreenGameModel(gameName)
                if (result.isError) {
                    showError()
                } else {
                    navigator.showGameFragment(result.successResult)
                }
            }
        } else {
            Toast.makeText(
                context,
                getString(R.string.need_login_error_message),
                Toast.LENGTH_SHORT
            ).show()
            binding.rvMain.show()
            binding.root.postDelayed({
                vLoadingBackground.hide()
                pbLoading.hide()
            }, ANIMATE_LOADING)
        }
    }

    private fun showError() {
        with(binding) {
            root.postDelayed({
                vLoadingBackground.hide()
                pbLoading.hide()
            }, ANIMATE_LOADING)
            rvMain.hide()
            ivError.show()
        }
    }
}

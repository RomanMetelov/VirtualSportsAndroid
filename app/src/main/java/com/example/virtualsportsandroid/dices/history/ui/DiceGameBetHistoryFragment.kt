package com.example.virtualsportsandroid.dices.history.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualsportsandroid.Application
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.DiceGameBetHistoryFragmentBinding
import com.example.virtualsportsandroid.dices.game.domain.DiceGameResultModel
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import com.example.virtualsportsandroid.utils.ui.hide
import com.example.virtualsportsandroid.utils.ui.show
import com.google.android.material.transition.platform.MaterialFadeThrough
import javax.inject.Inject

class DiceGameBetHistoryFragment :
    BaseFragment(R.layout.dice_game_bet_history_fragment) {

    private lateinit var binding: DiceGameBetHistoryFragmentBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DiceGameBetHistoryViewModel

    private val betHistoryAdapter: BetHistoryAdapter by lazy {
        BetHistoryAdapter()
    }

    companion object {
        fun newInstance() = DiceGameBetHistoryFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        setupDi()
        binding = DiceGameBetHistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DiceGameBetHistoryFragmentBinding.bind(view)
        setupViewModel()
        setupRecyclerViews()
        setupListeners()
        viewModel.loadData()
    }

    private fun setupDi() {
        val app = requireActivity().application as Application
        app.getComponent().inject(this)
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener { navigator.back() }
    }

    private fun setupViewModel() {
        (requireActivity().application as Application).getComponent().inject(this)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(DiceGameBetHistoryViewModel::class.java)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.diceGameBetHistoryFragmentStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is DiceGameBetHistoryFragmentState.Loading -> showLoading()
                is DiceGameBetHistoryFragmentState.Error -> showError(it.errorMessage)
                is DiceGameBetHistoryFragmentState.Content -> showContent(it.last100Bets)
            }
        }
    }

    private fun setupRecyclerViews() {
        binding.rvBetHistory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = betHistoryAdapter
            val itemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
            ContextCompat.getDrawable(context, R.drawable.divider)?.let {
                itemDecoration.setDrawable(it)
            }
            addItemDecoration(itemDecoration)
        }
    }

    private fun showContent(betHistory: List<DiceGameResultModel>) {
        with(binding) {
            rvBetHistory.show()
            tvErrorMessage.hide()
            pbLoading.hide()
        }
        betHistoryAdapter.submitList(betHistory)
    }

    private fun showError(errorMessage: String) {
        with(binding) {
            rvBetHistory.hide()
            tvErrorMessage.hide()
            pbLoading.hide()
            tvErrorMessage.apply {
                show()
                text = errorMessage
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            rvBetHistory.hide()
            tvErrorMessage.hide()
            pbLoading.show()
        }
    }
}

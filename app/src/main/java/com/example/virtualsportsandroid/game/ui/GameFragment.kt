@file:Suppress("TooManyFunctions")

package com.example.virtualsportsandroid.game.ui

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.virtualsportsandroid.Application
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.GameFragmentBinding
import com.example.virtualsportsandroid.game.GameFragmentNavigator
import com.example.virtualsportsandroid.game.data.ScreenGameModel
import com.example.virtualsportsandroid.game.data.api.GameScreenErrorType
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import com.google.android.material.transition.platform.MaterialFadeThrough
import javax.inject.Inject


class GameFragment : BaseFragment(R.layout.game_fragment) {


    private lateinit var game: ScreenGameModel
    private lateinit var binding: GameFragmentBinding
    private lateinit var ivAddToFavorite: AppCompatImageView
    private lateinit var ivDelFromFavorite: AppCompatImageView
    private lateinit var ivHeart: AppCompatImageView
    private lateinit var emptyHeart: AnimatedVectorDrawable
    private lateinit var fillHeart: AnimatedVectorDrawable

    private var isFavorite = false
        set(value) {
            field = value
            animateHeart()
        }

    private val gameFragmentNavigator: GameFragmentNavigator by lazy {
        GameFragmentNavigator(
            childFragmentManager,
            R.id.container
        )
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: GameFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        arguments?.let {
            game = it.getParcelable(GAME_KEY) ?: ScreenGameModel("", "", "")
        }
        when (game.id) {
            DICE_GAME_ID -> gameFragmentNavigator.showDiceFragment()
            else -> gameFragmentNavigator.showWebViewFragment(game)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        setupDi()
        binding = GameFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GameFragmentViewModel::class.java)
        setupListeners()
        observeLiveData()
        viewModel.playGame(game)
        initViews()
        showGameInfo()
    }

    private fun showGameInfo() {
        binding.tvGameTitle.text = game.displayName
        if (game.isFavorite) isFavorite = game.isFavorite
    }

    private fun initViews() {
        ivAddToFavorite = binding.ivAddToFavourites
        ivDelFromFavorite = binding.ivDelFromFavourites
        ivHeart = binding.ivHeart
        emptyHeart = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.avd_heart_empty
        ) as AnimatedVectorDrawable
        fillHeart = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.avd_heart_fill
        ) as AnimatedVectorDrawable
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener { navigator.back() }
        binding.ivHeart.setOnClickListener {
            changeGameFavorite()
        }
    }

    private fun observeLiveData() {
        viewModel.changeGameFavoriteResultLiveData.observe(viewLifecycleOwner, { result ->
            if (!result.isError) {
                if (result.successResult) game.isFavorite = !game.isFavorite
            } else {
                handleErrorOnUi(result.errorResult)
                isFavorite = !isFavorite
            }
        })
        viewModel.playGameResultViewModel.observe(viewLifecycleOwner, { result ->
            if (result.isError) handleErrorOnUi(result.errorResult)
        })
    }

    private fun handleErrorOnUi(errorType: GameScreenErrorType) {
        when (errorType) {
            GameScreenErrorType.NETWORK_ERROR -> navigator.showNoNetworkFragment()
            GameScreenErrorType.UNAUTHORIZED -> {
                navigator.showMainFragment()
                Toast.makeText(
                    context, getString(errorType.errorMessage), Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
                Toast.makeText(
                    context, getString(R.string.change_favorite_game_text), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun changeGameFavorite() {
        viewModel.changeGameFavorite(game)
        isFavorite = !isFavorite
    }

    private fun setupDi() {
        val app = requireActivity().application as Application
        app.getComponent().inject(this)
    }

    private fun animateHeart() {
        val drawable: AnimatedVectorDrawable = if (isFavorite) fillHeart else emptyHeart
        ivHeart.setImageDrawable(drawable)
        drawable.start()
    }

    companion object {
        private const val DICE_GAME_ID = "original_dice_game"
        private const val GAME_KEY = "GAME_KEY"

        fun newInstance(gameModel: ScreenGameModel): GameFragment = GameFragment().apply {
            arguments = Bundle().apply {
                putParcelable(GAME_KEY, gameModel)
            }
        }
    }
}

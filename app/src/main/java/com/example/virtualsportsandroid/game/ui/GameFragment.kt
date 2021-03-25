@file:Suppress("TooManyFunctions")

package com.example.virtualsportsandroid.game.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import com.example.virtualsportsandroid.Application
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.GameFragmentBinding
import com.example.virtualsportsandroid.game.data.ScreenGameModel
import com.example.virtualsportsandroid.game.data.api.GameScreenErrorType
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import com.example.virtualsportsandroid.utils.ui.hide
import com.example.virtualsportsandroid.utils.ui.isVisible
import com.example.virtualsportsandroid.utils.ui.show
import javax.inject.Inject

class GameFragment : BaseFragment(R.layout.game_fragment) {

    private lateinit var game: ScreenGameModel
    private lateinit var binding: GameFragmentBinding
    private lateinit var webView: WebView
    private lateinit var ivAddToFavorite: AppCompatImageView
    private lateinit var ivDelFromFavorite: AppCompatImageView

    @Inject
    lateinit var viewModel: GameFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            game = it.getParcelable(GAME_KEY) ?: ScreenGameModel("", "", "")
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
        initViews()
        setupListeners()
        observeLiveData()
        showGameInfo()
        handleGameFavorite()
        binding.tvGameTitle.text = game.displayName
    }

    private fun handleGameFavorite() {
        when (game.isFavorite) {
            true -> {
                ivAddToFavorite.hide()
                ivDelFromFavorite.show()
            }
            false -> {
                ivAddToFavorite.show()
                ivDelFromFavorite.hide()
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun showGameInfo() {
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(game.url)
                return true
            }
        }
        webView.loadUrl(game.url)
    }

    private fun initViews() {
        webView = binding.webView
        ivAddToFavorite = binding.ivAddToFavourites
        ivDelFromFavorite = binding.ivDelFromFavourites
    }

    private fun setupListeners() {
        ivAddToFavorite.setOnClickListener { changeGameFavorite() }
        ivDelFromFavorite.setOnClickListener { changeGameFavorite() }
    }

    private fun observeLiveData() {
        viewModel.changeGameFavoriteResultLiveData.observe(viewLifecycleOwner, { result ->
            if (!result.isError) {
                if (result.successResult) game.isFavorite = !game.isFavorite
            } else {
                handleErrorOnUi(result.errorResult)
                changeFavoriteStarView()
            }
        })
    }

    private fun handleErrorOnUi(errorType: GameScreenErrorType) {
        when (errorType) {
            GameScreenErrorType.NETWORK_ERROR -> navigator.showNoNetworkFragment()
            else -> {
                Toast.makeText(
                    context,
                    getString(R.string.change_favorite_game_text),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun changeGameFavorite() {
        viewModel.changeGameFavorite(game)
        changeFavoriteStarView()
    }

    private fun changeFavoriteStarView() {
        if (ivAddToFavorite.isVisible()) {
            ivDelFromFavorite.show()
            ivAddToFavorite.hide()
        } else {
            ivDelFromFavorite.hide()
            ivAddToFavorite.show()
        }
    }

    private fun setupDi() {
        val app = requireActivity().application as Application
        app.getComponent().inject(this)
    }

    companion object {
        private const val GAME_KEY = "GAME_KEY"

        fun newInstance(gameModel: ScreenGameModel): GameFragment = GameFragment().apply {
            arguments = Bundle().apply {
                putParcelable(GAME_KEY, gameModel)
            }
        }
    }
}

package com.example.virtualsportsandroid.game.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.game.data.ScreenGameModel
import com.example.virtualsportsandroid.utils.ui.BaseFragment

class WebViewFragment : BaseFragment(R.layout.webview_fragment) {

    companion object {
        private const val GAME_KEY = "GAME_KEY"

        fun newInstance(gameModel: ScreenGameModel): WebViewFragment = WebViewFragment().apply {
            arguments = Bundle().apply {
                putParcelable(GAME_KEY, gameModel)
            }
        }
    }

    private lateinit var game: ScreenGameModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            game = it.getParcelable(GAME_KEY) ?: ScreenGameModel("", "", "")
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webView: WebView = view.findViewById(R.id.web_view)

        webView.settings.javaScriptEnabled = true
        webView.loadUrl(game.url)
    }
}

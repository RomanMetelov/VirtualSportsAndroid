package com.example.virtualsportsandroid.games.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.GameItemBinding
import com.example.virtualsportsandroid.game.data.ScreenGameModel
import com.example.virtualsportsandroid.games.domain.model.GameDiffCallback
import com.example.virtualsportsandroid.games.domain.model.GameModel
import com.example.virtualsportsandroid.utils.ui.loadImageFromURLWithPlaceholder

class GamesListAdapter(
    private val open: (String) -> Unit
):
    ListAdapter<GameModel, GamesListAdapter.GameViewHolder>(GameDiffCallback()) {

    class GameViewHolder(private val binding: GameItemBinding,
                         private val open: (String) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gameModel: GameModel) {
            with(binding) {
                tvGameName.text = gameModel.displayName
                ivGameImage.loadImageFromURLWithPlaceholder(
                    gameModel.imageURL,
                    R.drawable.game_image_placeholder
                )
                root.setOnClickListener {
                    open(gameModel.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = GameItemBinding.inflate(
            LayoutInflater.from(parent.context), null, false
        )
        return GameViewHolder(binding, open)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

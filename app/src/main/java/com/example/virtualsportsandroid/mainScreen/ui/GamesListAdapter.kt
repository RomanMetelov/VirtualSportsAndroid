package com.example.virtualsportsandroid.mainScreen.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualsportsandroid.databinding.GameItemBinding
import com.example.virtualsportsandroid.mainScreen.domain.model.GameDiffCallback
import com.example.virtualsportsandroid.mainScreen.domain.model.GameModel
import com.example.virtualsportsandroid.utils.ui.loadGameImageFromURL

class GamesListAdapter :
    ListAdapter<GameModel, GamesListAdapter.GameViewHolder>(GameDiffCallback()) {

    class GameViewHolder(private val binding: GameItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gameModel: GameModel) {
            with(binding) {
                tvGameName.text = gameModel.displayName
                ivGameImage.loadGameImageFromURL(gameModel.imageURL)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = GameItemBinding.inflate(
            LayoutInflater.from(parent.context), null, false
        )
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

package com.example.virtualsportsandroid.games.domain.model

import androidx.recyclerview.widget.DiffUtil

class GameDiffCallback : DiffUtil.ItemCallback<GameModel>() {
    override fun areItemsTheSame(oldItem: GameModel, newItem: GameModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GameModel, newItem: GameModel): Boolean {
        return oldItem == newItem
    }
}

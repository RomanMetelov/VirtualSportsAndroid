package com.example.virtualsportsandroid.games.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.virtualsportsandroid.games.domain.model.GamesList

class DiffTopGamesCallback(
    private val topGamesNewList: GamesList,
    private val topGamesOldList: GamesList?
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return topGamesOldList?.games?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return topGamesNewList.games.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return if (topGamesOldList == null) false
        else topGamesOldList.games[oldItemPosition] == topGamesNewList.games[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return if (topGamesOldList == null) false
        else topGamesOldList.games[oldItemPosition] == topGamesNewList.games[newItemPosition]
    }
}



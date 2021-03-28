package com.example.virtualsportsandroid.games.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.virtualsportsandroid.games.domain.model.GamesList

class DiffMainListScreenCallback(
    private val anotherGamesNewList: List<GamesList>,
    private val anotherGamesOldList: List<GamesList>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        var size = 0
        anotherGamesOldList.forEach {
            repeat(it.games.size) {
                size++
            }
        }
        return size
    }

    override fun getNewListSize(): Int {
        var size = 0
        anotherGamesNewList.forEach {
            repeat(it.games.size) {
                size++
            }
        }
        return size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return anotherGamesOldList[oldItemPosition] == anotherGamesNewList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return anotherGamesOldList[oldItemPosition] == anotherGamesNewList[newItemPosition]
    }
}
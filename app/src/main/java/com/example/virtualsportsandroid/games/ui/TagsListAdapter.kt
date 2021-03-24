package com.example.virtualsportsandroid.games.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualsportsandroid.databinding.GameListItemBinding
import com.example.virtualsportsandroid.games.domain.model.TagDiffCallback
import com.example.virtualsportsandroid.games.domain.model.TagModel

class TagsListAdapter : ListAdapter<TagModel, TagsListAdapter.TagViewHolder>(TagDiffCallback()) {
    class TagViewHolder(private val binding: GameListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private companion object {
            const val COLUMNS_COUNT = 2
        }

        fun bind(tagModel: TagModel) {
            with(binding) {
                tvTagName.text = tagModel.name
                rvGames.layoutManager = GridLayoutManager(root.context, COLUMNS_COUNT)
                rvGames.adapter = GamesListAdapter().apply {
                    submitList(tagModel.games)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val binding = GameListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

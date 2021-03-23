package com.example.virtualsportsandroid.mainScreen.domain.model

import androidx.recyclerview.widget.DiffUtil

class TagDiffCallback : DiffUtil.ItemCallback<TagModel>() {
    override fun areItemsTheSame(oldItem: TagModel, newItem: TagModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TagModel, newItem: TagModel): Boolean {
        return oldItem == newItem
    }
}
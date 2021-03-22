package com.example.virtualsportsandroid.filter.data.models

import androidx.recyclerview.widget.DiffUtil

class CategoryDiffCallback : DiffUtil.ItemCallback<CategoryResponse>() {
    override fun areItemsTheSame(oldItem: CategoryResponse, newItem: CategoryResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CategoryResponse, newItem: CategoryResponse): Boolean {
        return oldItem == newItem
    }
}

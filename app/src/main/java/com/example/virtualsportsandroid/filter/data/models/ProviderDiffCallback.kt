package com.example.virtualsportsandroid.filter.data.models

import androidx.recyclerview.widget.DiffUtil

class ProviderDiffCallback : DiffUtil.ItemCallback<ProviderResponse>() {
    override fun areItemsTheSame(oldItem: ProviderResponse, newItem: ProviderResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProviderResponse, newItem: ProviderResponse): Boolean {
        return oldItem == newItem
    }
}

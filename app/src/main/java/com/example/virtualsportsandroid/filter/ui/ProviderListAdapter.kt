package com.example.virtualsportsandroid.filter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualsportsandroid.databinding.ProviderItemBinding
import com.example.virtualsportsandroid.filter.data.models.ProviderDiffCallback
import com.example.virtualsportsandroid.filter.data.models.ProviderResponse
import com.example.virtualsportsandroid.utils.ui.loadImageFromURL

class ProviderListAdapter :
    ListAdapter<ProviderResponse, ProviderListAdapter.ProviderViewHolder>(ProviderDiffCallback()) {

    class ProviderViewHolder(private val binding: ProviderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(provider: ProviderResponse) {
            binding.ivProviderImage.loadImageFromURL(provider.imageURL)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderViewHolder {
        val binding = ProviderItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProviderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProviderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

package com.example.virtualsportsandroid.filter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.ProviderItemBinding
import com.example.virtualsportsandroid.filter.data.models.ProviderDiffCallback
import com.example.virtualsportsandroid.filter.data.models.ProviderResponse
import com.example.virtualsportsandroid.utils.ui.loadImageFromURL

class ProviderListAdapter(
    private val selectProvider: (String) -> Unit,
    private val unselectProvider: (String) -> Unit,
    private val getSelectedProviders: () -> List<String>
) :
    ListAdapter<ProviderResponse, ProviderListAdapter.ProviderViewHolder>(ProviderDiffCallback()) {

    class ProviderViewHolder(
        private val binding: ProviderItemBinding,
        private val selectProvider: (String) -> Unit,
        private val unselectProvider: (String) -> Unit,
        private val getSelectedProviders: () -> List<String>
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(provider: ProviderResponse) {
            with(binding) {
                ivProviderImage.loadImageFromURL(provider.imageURL)
                root.setOnClickListener {
                    if (getSelectedProviders().contains(provider.id)) {
                        unselectProvider(provider.id)
                        it.setBackgroundResource(R.drawable.default_provider_item_background)
                    } else {
                        selectProvider(provider.id)
                        it.setBackgroundResource(R.drawable.selected_provider_item_background)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderViewHolder {
        val binding = ProviderItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProviderViewHolder(binding, selectProvider, unselectProvider, getSelectedProviders) 
    }

    override fun onBindViewHolder(holder: ProviderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

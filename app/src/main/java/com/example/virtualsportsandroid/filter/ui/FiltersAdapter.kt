package com.example.virtualsportsandroid.filter.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.CategoryItemBinding
import com.example.virtualsportsandroid.databinding.FilterBorderItemBinding
import com.example.virtualsportsandroid.databinding.FilterProvidersTitleItemBinding
import com.example.virtualsportsandroid.databinding.ProviderItemBinding
import com.example.virtualsportsandroid.main.data.CategoryResponse
import com.example.virtualsportsandroid.main.data.ProviderResponse
import com.example.virtualsportsandroid.utils.ui.loadImageFromURLWithPlaceholder

@Suppress("LongParameterList")
class FiltersAdapter(
    private val categories: List<CategoryResponse>,
    private val providers: List<ProviderResponse>,
    private val selectCategory: (String) -> Unit,
    private val unselectCategory: () -> Unit,
    private val getSelectedCategory: () -> String,
    private val selectProvider: (String) -> Unit,
    private val unselectProvider: (String) -> Unit,
    private val getSelectedProviders: () -> List<String>,
) : RecyclerView.Adapter<FiltersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiltersViewHolder {
        return when (ItemType.values()[viewType]) {
            ItemType.CATEGORY -> FiltersViewHolder.CategoryViewHolder(
                CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                selectCategory,
                unselectCategory,
                getSelectedCategory,
                { notifyItemRangeChanged(0, categories.size) }
            )
            ItemType.BORDER -> FiltersViewHolder.BorderViewHolder(
                FilterBorderItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            ItemType.PROVIDERS_TITLE -> FiltersViewHolder.ProvidersTitleViewHolder(
                FilterProvidersTitleItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            ItemType.PROVIDER -> FiltersViewHolder.ProviderViewHolder(
                ProviderItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ),
                selectProvider,
                unselectProvider,
                getSelectedProviders
            )
        }
    }

    override fun onBindViewHolder(holder: FiltersViewHolder, position: Int) {

        if (holder is FiltersViewHolder.CategoryViewHolder) {
            holder.bind(categories[position])

        } else if (holder is FiltersViewHolder.ProviderViewHolder) {
            holder.bind(providers[position - 1 - 1 - categories.size])
        }
    }

    override fun getItemCount(): Int {
        return categories.size + 1 + 1 + providers.size
    }

    override fun getItemViewType(position: Int) = when {
        position < categories.size -> ItemType.CATEGORY.ordinal
        position == categories.size -> ItemType.BORDER.ordinal
        position == (categories.size + 1) -> ItemType.PROVIDERS_TITLE.ordinal
        else -> ItemType.PROVIDER.ordinal
    }
}

sealed class FiltersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class CategoryViewHolder(
        private val binding: CategoryItemBinding,
        private val selectCategory: (String) -> Unit,
        private val unselectCategory: () -> Unit,
        private val getSelectedCategory: () -> String,
        private val update: () -> Unit
    ) :
        FiltersViewHolder(binding.root) {
        fun bind(category: CategoryResponse) {
            with(binding) {
                tvCategoryName.text = category.name
                category.imageURL.let {
                    if (it.isNotEmpty()) {
                        ivCategoryIcon.loadImageFromURLWithPlaceholder(
                            category.imageURL,
                            R.drawable.ic_default_category
                        )
                    } else {
                        ivCategoryIcon.setImageResource(0)
                    }
                }
                root.setOnClickListener {
                    if (getSelectedCategory() != category.id) {
                        selectCategory(category.id)
                    } else {
                        unselectCategory()
                    }
                    update()
                }
                if (category.id == getSelectedCategory()) root.setBackgroundResource(R.color.gray_light_4a)
                else root.setBackgroundResource(R.color.gray_background)

            }
        }
    }

    class ProviderViewHolder(
        private val binding: ProviderItemBinding,
        private val selectProvider: (String) -> Unit,
        private val unselectProvider: (String) -> Unit,
        private val getSelectedProviders: () -> List<String>
    ) : FiltersViewHolder(binding.root) {
        fun bind(provider: ProviderResponse) {
            with(binding) {
                ivProviderImage.loadImageFromURLWithPlaceholder(
                    provider.imageURL,
                    R.drawable.ic_default_provider
                )
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

    class BorderViewHolder(binding: FilterBorderItemBinding) :
        FiltersViewHolder(binding.root)

    class ProvidersTitleViewHolder(binding: FilterProvidersTitleItemBinding) :
        FiltersViewHolder(binding.root)
}

enum class ItemType {
    CATEGORY, PROVIDER, BORDER, PROVIDERS_TITLE
}

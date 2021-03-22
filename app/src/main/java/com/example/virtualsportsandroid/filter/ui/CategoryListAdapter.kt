package com.example.virtualsportsandroid.filter.ui

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.CategoryItemBinding
import com.example.virtualsportsandroid.filter.data.models.CategoryDiffCallback
import com.example.virtualsportsandroid.filter.data.models.CategoryResponse
import com.example.virtualsportsandroid.utils.ui.loadImageFromURL

class CategoryListAdapter(
    private val selectCategory: (String) -> Unit,
    private val unselectCategory: () -> Unit,
    private val getSelectedCategory: () -> String
) :
    ListAdapter<CategoryResponse, CategoryListAdapter.CategoryViewHolder>(CategoryDiffCallback()) {


    class CategoryViewHolder(
        private val binding: CategoryItemBinding,
        private val selectCategory: (String) -> Unit,
        private val unselectCategory: () -> Unit,
        private val getSelectedCategory: () -> String,
        private val update: () -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryResponse) {
            with(binding) {
                tvCategoryName.text = category.name
                category.imageURL.let {
                    if (it.isNotEmpty()) {
                        ivCategoryIcon.loadImageFromURL(category.imageURL)
                    } else {
                        ivCategoryIcon.setImageResource(0)
                    }
                }
                root.setOnClickListener {
                    if(getSelectedCategory() != category.id) {
                        selectCategory(category.id)
                    } else {
                        unselectCategory()
                    }
                    update()
                }
                if (category.id == getSelectedCategory()) {
                    tvCategoryName.typeface = Typeface.DEFAULT_BOLD
                    root.setBackgroundResource(R.color.gray_light_4a)
                } else {
                    tvCategoryName.typeface = Typeface.DEFAULT
                    root.setBackgroundResource(R.color.gray_background)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding, selectCategory, unselectCategory, getSelectedCategory) {
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

package com.example.virtualsportsandroid.filter.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualsportsandroid.Application
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.FilterFragmentBinding
import com.example.virtualsportsandroid.loadingConfigs.data.CategoryResponse
import com.example.virtualsportsandroid.loadingConfigs.data.ProviderResponse
import com.example.virtualsportsandroid.main.ui.MainFragmentNavigator
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import com.example.virtualsportsandroid.utils.ui.hide
import com.example.virtualsportsandroid.utils.ui.show
import javax.inject.Inject

class FilterFragment : BaseFragment(R.layout.filter_fragment) {

    companion object {
        fun newInstance(mainFragmentNavigator: MainFragmentNavigator) =
            FilterFragment().apply {
                this.mainFragmentNavigator = mainFragmentNavigator
            }
    }

    @Inject
    lateinit var viewModel: FilterViewModel
    private lateinit var binding: FilterFragmentBinding
    private lateinit var mainFragmentNavigator: MainFragmentNavigator
    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter(viewModel::selectCategory, viewModel::unselectCategory) {
            viewModel.selectedCategoryLiveData.value.toString()
        }
    }
    private val providerAdapter: ProviderListAdapter by lazy {
        ProviderListAdapter(viewModel::selectProvider, viewModel::unselectProvider) {
            viewModel.selectedProvidersLiveData.value ?: emptyList()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FilterFragmentBinding.bind(view)
        setupViewModel()
        observeFragmentState()
        setupRecyclerViews()
        observeSelectedItems()
        setupListeners()
        viewModel.loadData()
    }

    private fun setupListeners() {
        with(binding) {
            ivClose.setOnClickListener {
                mainFragmentNavigator.back()
            }
            btnApply.setOnClickListener {
                val category = viewModel.selectedCategoryLiveData.value
                var providers = viewModel.selectedProvidersLiveData.value
                if (providers != null && providers.isEmpty()) {
                    providers = null
                }
                mainFragmentNavigator.showGamesFragment(category, providers)
            }
        }
    }

    private fun setupViewModel() {
        (requireActivity().application as Application).getComponent().inject(this)
    }

    private fun observeFragmentState() {
        viewModel.filterFragmentStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is FilterFragmentState.Loading -> showLoading()
                is FilterFragmentState.Error -> showError(it.errorMessage)
                is FilterFragmentState.Content -> showContent(it.categories, it.providers)
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            pbLoading.show()
            tvFiltersTitle.hide()
            ivClose.hide()
            filtersBorder.hide()
            scContent.hide()
            tvErrorMessage.hide()
        }
    }


    private fun showContent(categories: List<CategoryResponse>, providers: List<ProviderResponse>) {
        with(binding) {
            tvFiltersTitle.show()
            ivClose.show()
            filtersBorder.show()
            scContent.show()
            tvErrorMessage.hide()
            pbLoading.hide()
        }
        categoryAdapter.submitList(categories)
        providerAdapter.submitList(providers)
    }

    private fun showError(errorMessage: String) {
        with(binding) {
            tvFiltersTitle.hide()
            ivClose.hide()
            filtersBorder.hide()
            scContent.hide()
            btnApply.hide()
            tvErrorMessage.apply {
                show()
                text = errorMessage
            }
            pbLoading.hide()
        }
    }

    private fun setupRecyclerViews() {
        with(binding) {
            rvCategories.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = categoryAdapter
            }
            rvProviders.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = providerAdapter
            }
        }
    }

    private fun observeSelectedItems() {
        viewModel.selectedCategoryLiveData.observe(viewLifecycleOwner) {
            if (it == null && viewModel.selectedProvidersLiveData.value.isNullOrEmpty()) {
                binding.btnApply.hide()
            } else {
                binding.btnApply.show()
            }
        }
        viewModel.selectedProvidersLiveData.observe(viewLifecycleOwner) {
            if (viewModel.selectedCategoryLiveData.value == null && it.isNullOrEmpty()) {
                binding.btnApply.hide()
            } else {
                binding.btnApply.show()
            }
        }
    }
}

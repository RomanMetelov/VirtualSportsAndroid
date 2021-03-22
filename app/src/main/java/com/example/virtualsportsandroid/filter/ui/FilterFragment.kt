package com.example.virtualsportsandroid.filter.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualsportsandroid.Application
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.FilterFragmentBinding
import com.example.virtualsportsandroid.filter.data.models.CategoryResponse
import com.example.virtualsportsandroid.filter.data.models.ProviderResponse
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import com.example.virtualsportsandroid.utils.ui.hide
import com.example.virtualsportsandroid.utils.ui.show
import javax.inject.Inject

class FilterFragment private constructor() : BaseFragment(R.layout.filter_fragment) {

    companion object {
        fun newInstance() = FilterFragment()
    }

    @Inject
    lateinit var viewModel: FilterViewModel
    private lateinit var binding: FilterFragmentBinding
    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter(viewModel::selectCategory) {
            viewModel.selectedCategoryLiveData.value.toString()
        }
    }
    private val providerAdapter = ProviderListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FilterFragmentBinding.bind(view)
        setupViewModel()
        setupRecyclerViews()
        setupListeners()
        viewModel.loadData()
    }

    private fun setupListeners() {
        with(binding) {
            ivClose.setOnClickListener {
                navigator.back()
            }
            header.btnLogin.setOnClickListener {
                navigator.showLoginFragment()
            }
            header.btnSignUp.setOnClickListener {
                navigator.showRegistrationFragment()
            }
        }
    }

    private fun setupViewModel() {
        (requireActivity().application as Application).getComponent().inject(this)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.filterFragmentStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is FilterFragmentState.Loading -> showLoading()
                is FilterFragmentState.Error -> showError(it.errorMessage)
                is FilterFragmentState.Content -> showContent(it.categories, it.providers)
            }
        }
    }

    private fun showContent(categories: List<CategoryResponse>, providers: List<ProviderResponse>) {
        with(binding) {
            header.headerContainer.show()
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
            header.headerContainer.hide()
            tvFiltersTitle.hide()
            ivClose.hide()
            filtersBorder.hide()
            scContent.hide()
            btnApply.hide()
            tvErrorMessage.hide()
            pbLoading.hide()
            tvErrorMessage.apply {
                show()
                text = errorMessage
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            header.headerContainer.hide()
            tvFiltersTitle.hide()
            ivClose.hide()
            filtersBorder.hide()
            scContent.hide()
            btnApply.hide()
            tvErrorMessage.hide()
            pbLoading.show()
        }
    }

    private fun setupRecyclerViews() {
        with(binding) {
            rvCategories.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = categoryAdapter
            }
            viewModel.selectedCategoryLiveData.observe(viewLifecycleOwner) {
                if (!btnApply.isVisible && it != null) {
                    btnApply.show()
                }
            }
            rvProviders.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = providerAdapter
            }
        }
    }
}
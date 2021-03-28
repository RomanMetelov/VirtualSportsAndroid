package com.example.virtualsportsandroid.filter.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualsportsandroid.Application
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.FilterFragmentBinding
import com.example.virtualsportsandroid.databinding.GameFragmentBinding
import com.example.virtualsportsandroid.main.data.CategoryResponse
import com.example.virtualsportsandroid.main.data.ProviderResponse
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import com.example.virtualsportsandroid.utils.ui.hide
import com.example.virtualsportsandroid.utils.ui.show
import javax.inject.Inject

private const val LOG_TAG = "QZFILTER_FRAGMENT"

class FilterFragment : BaseFragment(R.layout.filter_fragment) {

    companion object {
        fun newInstance(
            back: () -> Unit,
            showGamesFragment: (category: String?, providers: List<String>?) -> Unit
        ) =
            FilterFragment().apply {
                this.back = back
                this.showGamesFragment = showGamesFragment
            }
    }

    @Inject
    lateinit var viewModel: FilterViewModel
    private lateinit var binding: FilterFragmentBinding
    private lateinit var back: () -> Unit
    private lateinit var showGamesFragment: (category: String?, providers: List<String>?) -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "onCreate")
        setupViewModel()
        viewModel.loadData()
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        Log.d(LOG_TAG, "onAttach")
        super.onAttach(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(LOG_TAG, "onCreateView")
        binding = FilterFragmentBinding.inflate(inflater, container, false)
        observeFragmentState()
        observeSelectedItems()
        setupListeners()
        return binding.root

    }

    override fun onDestroy() {
        Log.d(LOG_TAG, "onDestroy")
        super.onDestroy()

    }

    override fun onDestroyView() {
        Log.d(LOG_TAG, "onDestroyView")
        super.onDestroyView()
    }



    override fun onDetach() {
        Log.d(LOG_TAG, "onDetach")
        super.onDetach()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Log.d(LOG_TAG, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(LOG_TAG, "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d(LOG_TAG, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(LOG_TAG, "onStop")
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(LOG_TAG, "onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }

    private fun setupListeners() {
        with(binding) {
            ivClose.setOnClickListener {
                back()
            }
            btnApply.setOnClickListener {
                val category = viewModel.selectedCategoryLiveData.value
                var providers = viewModel.selectedProvidersLiveData.value
                if (providers != null && providers.isEmpty()) {
                    providers = null
                }
                showGamesFragment(category, providers)
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
                is FilterFragmentState.Error -> showError()
                is FilterFragmentState.Content -> showContent(it.categories, it.providers)
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            tvFiltersTitle.hide()
            ivClose.hide()
            filtersBorder.hide()
            rvFilters.hide()
            btnApply.hide()
            ivError.hide()
            pbLoading.show()
        }
    }


    private fun showContent(categories: List<CategoryResponse>, providers: List<ProviderResponse>) {
        with(binding) {
            ivError.hide()
            pbLoading.hide()
            tvFiltersTitle.show()
            ivClose.show()
            filtersBorder.show()
            btnApply.hide()
            with(rvFilters) {
                layoutManager = LinearLayoutManager(context)
                adapter = FiltersAdapter(
                    categories,
                    providers,
                    viewModel::selectCategory,
                    viewModel::unselectCategory,
                    { viewModel.selectedCategoryLiveData.value.toString() },
                    viewModel::selectProvider,
                    viewModel::unselectProvider
                ) { viewModel.selectedProvidersLiveData.value ?: emptyList() }
                show()
            }
        }
    }

    private fun showError() {
        with(binding) {
            tvFiltersTitle.hide()
            ivClose.hide()
            filtersBorder.hide()
            rvFilters.hide()
            btnApply.hide()
            pbLoading.hide()
            ivError.show()
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

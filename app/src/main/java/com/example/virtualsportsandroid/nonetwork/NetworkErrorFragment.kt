package com.example.virtualsportsandroid.nonetwork

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.virtualsportsandroid.Application
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.NonetworkFragmentBinding
import com.example.virtualsportsandroid.utils.NetworkHelper
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import javax.inject.Inject

class NetworkErrorFragment : BaseFragment(R.layout.nonetwork_fragment) {

    private lateinit var binding: NonetworkFragmentBinding

    @Inject
    lateinit var networkHelper: NetworkHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupDi()
        binding = NonetworkFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnRetry.setOnClickListener {
            if (networkHelper.isNetworkConnected()) requireActivity().onBackPressed()
        }
    }

    private fun setupDi() {
        val app = requireActivity().application as Application
        app.getComponent().inject(this)
    }

    companion object {
        fun newInstance(): NetworkErrorFragment {
            return NetworkErrorFragment()
        }
    }
}

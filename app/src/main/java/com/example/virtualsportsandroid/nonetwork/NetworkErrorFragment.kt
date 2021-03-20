package com.example.virtualsportsandroid.nonetwork

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.NonetworkFragmentBinding
import com.example.virtualsportsandroid.utils.ui.BaseFragment

class NetworkErrorFragment : BaseFragment(R.layout.nonetwork_fragment) {

    private lateinit var binding: NonetworkFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NonetworkFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnRetry.setOnClickListener {
            if (isNetworkConnected()) {
                requireActivity().onBackPressed()
            }
        }
    }

    companion object {
        fun newInstance(): NetworkErrorFragment {
            return NetworkErrorFragment()
        }
    }
}

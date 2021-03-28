package com.example.virtualsportsandroid.nonetwork

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.virtualsportsandroid.Application
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.NonetworkFragmentBinding
import com.example.virtualsportsandroid.utils.NetworkHelper
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import javax.inject.Inject

private const val LOG_TAG = "QZNETWORK_ERR_FRAGMENT"

@Suppress("TooManyFunctions", "LongParameterList")
class NetworkErrorFragment : BaseFragment(R.layout.nonetwork_fragment) {

    private lateinit var binding: NonetworkFragmentBinding

    @Inject
    lateinit var networkHelper: NetworkHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(LOG_TAG, "onCreateView")
        setupDi()
        binding = NonetworkFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "onViewCreated")
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

    override fun onAttach(context: Context) {
        Log.d(LOG_TAG, "onAttach")
        super.onAttach(context)

    }

    override fun onDestroy() {
        Log.d(LOG_TAG, "onDestroy")
        super.onDestroy()

    }

    override fun onDestroyView() {
        Log.d(LOG_TAG, "onDestroyView")
        super.onDestroyView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "onCreate")
        super.onCreate(savedInstanceState)
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

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(LOG_TAG, "onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        Log.d(LOG_TAG, "onStop")
        super.onStop()
    }

}

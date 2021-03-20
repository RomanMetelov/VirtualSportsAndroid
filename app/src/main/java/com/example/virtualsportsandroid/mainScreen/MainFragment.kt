package com.example.virtualsportsandroid.mainScreen

import android.os.Bundle
import android.view.View
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.MainFragmentBinding
import com.example.virtualsportsandroid.utils.ui.BaseFragment

class MainFragment : BaseFragment(R.layout.main_fragment) {

    private lateinit var binding: MainFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)
    }
}
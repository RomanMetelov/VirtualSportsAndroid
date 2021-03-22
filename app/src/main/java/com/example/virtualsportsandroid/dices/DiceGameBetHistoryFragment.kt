package com.example.virtualsportsandroid.dices

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.virtualsportsandroid.R

class DiceGameBetHistoryFragment : Fragment() {

    companion object {
        fun newInstance() = DiceGameBetHistoryFragment()
    }

    private lateinit var viewModel: DiceGameBetHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dice_bet_history_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DiceGameBetHistoryViewModel::class.java)
        // ТУДУ: Use the ViewModel
    }

}

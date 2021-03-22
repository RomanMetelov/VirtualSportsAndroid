package com.example.virtualsportsandroid.dices

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.virtualsportsandroid.R

class DiceGameFragment : Fragment() {

    companion object {
        fun newInstance() = DiceGameFragment()
    }

    private lateinit var viewModel: DiceGameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dice_game_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DiceGameViewModel::class.java)
        // ТУДУ: Use the ViewModel
    }

}

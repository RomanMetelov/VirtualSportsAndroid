package com.example.virtualsportsandroid.dices.history.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.DiceBetHistoryItemBinding
import com.example.virtualsportsandroid.dices.game.domain.DiceGameResultModel
import com.example.virtualsportsandroid.utils.ui.hide
import com.example.virtualsportsandroid.utils.ui.show

class BetHistoryAdapter :
    ListAdapter<DiceGameResultModel, BetHistoryItemViewHolder>(ReposDiffCallBack()) {

    override fun onBindViewHolder(holder: BetHistoryItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BetHistoryItemViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.dice_bet_history_item, parent, false)
        return BetHistoryItemViewHolder(view)
    }
}

class BetHistoryItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var binding: DiceBetHistoryItemBinding

    fun onBind(diceGameResultModel: DiceGameResultModel) {
        binding = DiceBetHistoryItemBinding.bind(itemView)
        binding.apply {
            tvBetId.text = String.format(
                itemView.resources.getString(R.string.bet_history_item_id, diceGameResultModel.id)
            )
            tvBetDate.text = diceGameResultModel.datetime
            tvBetValue.text = diceGameResultModel.betType.stringValue

            diceGameResultModel.droppedNumber.apply {
                val droppedNumber = this
                if (droppedNumber % 2 == 0) {
                    tvBetResultValue.text = String.format(
                        itemView.resources.getString(R.string.bet_history_item_bet_result_value),
                        droppedNumber, "Even"
                    )
                } else {
                    tvBetResultValue.text = String.format(
                        itemView.resources.getString(R.string.bet_history_item_bet_result_value),
                        droppedNumber, "Odd"
                    )
                }
            }

            if (diceGameResultModel.isBetWon) {
                tvBetOutcomeWin.text = String.format(
                    itemView.resources.getString(R.string.win)
                )
                tvBetOutcomeWin.show()
                tvBetOutcomeLose.hide()
            } else {
                tvBetOutcomeLose.text = String.format(
                    itemView.resources.getString(R.string.lose)
                )
                tvBetOutcomeLose.show()
                tvBetOutcomeWin.hide()
            }

        }
    }
}

class ReposDiffCallBack : DiffUtil.ItemCallback<DiceGameResultModel>() {
    override fun areItemsTheSame(
        oldItem: DiceGameResultModel,
        newItem: DiceGameResultModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: DiceGameResultModel,
        newItem: DiceGameResultModel
    ): Boolean {
        return oldItem.id == newItem.id
    }
}


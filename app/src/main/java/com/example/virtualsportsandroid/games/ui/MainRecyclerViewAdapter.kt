package com.example.virtualsportsandroid.games.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.FilterButtonItemBinding
import com.example.virtualsportsandroid.databinding.GamesItemBinding
import com.example.virtualsportsandroid.games.domain.model.GamesList

class MainRecyclerViewAdapter(
    private val filterButtonOnClick: () -> Unit,
    private val firstTagGames: GamesList?,
    private val anotherGames: List<GamesList>
) : RecyclerView.Adapter<MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (ItemType.values()[viewType]) {
            ItemType.FILTER_BUTTON -> {
                MainViewHolder.FilterButtonViewHolder(
                    FilterButtonItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ItemType.FIRST_TAG_GAMES -> {
                MainViewHolder.FirstTagGames(
                    GamesItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ItemType.ANOTHER_GAMES -> {
                MainViewHolder.AnotherGames(
                    GamesItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        when (holder) {
            is MainViewHolder.FilterButtonViewHolder -> holder.bind(filterButtonOnClick)
            is MainViewHolder.FirstTagGames -> firstTagGames?.let { holder.bind(it) }
            is MainViewHolder.AnotherGames -> {
                if (firstTagGames == null) {
                    holder.bind(anotherGames[position - 1])
                } else {
                    holder.bind(anotherGames[position - 2])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        firstTagGames?.let {
            return 1 + anotherGames.size + 1
        }
        return 1 + anotherGames.size
    }

    override fun getItemViewType(position: Int) = when {
        position == 0 -> ItemType.FILTER_BUTTON.ordinal
        position == 1 && firstTagGames != null -> ItemType.FIRST_TAG_GAMES.ordinal
        else -> ItemType.ANOTHER_GAMES.ordinal
    }

}

enum class ItemType {
    FILTER_BUTTON, FIRST_TAG_GAMES, ANOTHER_GAMES
}

sealed class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class FilterButtonViewHolder(private val binding: FilterButtonItemBinding) :
        MainViewHolder(binding.root) {
        fun bind(onClick: () -> Unit) {
            binding.llFilterButton.setOnClickListener {
                onClick()
            }
        }
    }

    class FirstTagGames(private val binding: GamesItemBinding) :
        MainViewHolder(binding.root) {
        fun bind(tag: GamesList) {
            with(binding) {
                tvGamesListTitle.text = tag.name
                with(rvGames) {
                    layoutManager = LinearLayoutManager(
                        binding.root.context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    addItemDecoration(
                        GamesItemDecorator(
                            resources.getDimension(R.dimen.first_tag_games_space_between).toInt()
                        )
                    )
                    adapter = GamesListAdapter().apply {
                        submitList(tag.games)
                    }
                }
            }
        }
    }

    class AnotherGames(private val binding: GamesItemBinding) :
        MainViewHolder(binding.root) {
        private companion object {
            const val COLUMNS_COUNT = 2
        }

        fun bind(gamesList: GamesList) {
            with(binding) {
                tvGamesListTitle.text = gamesList.name
                with(rvGames) {
                    layoutManager = GridLayoutManager(context, COLUMNS_COUNT)
                    adapter = GamesListAdapter().apply {
                        submitList(gamesList.games)
                    }
                }
            }
        }
    }
}

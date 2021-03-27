package com.example.virtualsportsandroid.dices.game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.virtualsportsandroid.Application
import com.example.virtualsportsandroid.R
import com.example.virtualsportsandroid.databinding.DiceGameFragmentBinding
import com.example.virtualsportsandroid.dices.game.domain.DiceGameResultModel
import com.example.virtualsportsandroid.game.data.ScreenGameModel
import com.example.virtualsportsandroid.game.data.api.GameScreenErrorType
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import com.example.virtualsportsandroid.utils.ui.BaseFragment
import com.example.virtualsportsandroid.utils.ui.hide
import com.example.virtualsportsandroid.utils.ui.isVisible
import com.example.virtualsportsandroid.utils.ui.show
import kotlinx.android.synthetic.main.dice_game_fragment.ivAddToFavourites
import kotlinx.android.synthetic.main.game_fragment.ivDelFromFavourites
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

@Suppress("TooManyFunctions", "MagicNumber", "ComplexMethod")
class DiceGameFragment :
    BaseFragment(R.layout.dice_game_fragment) {

    private lateinit var binding: DiceGameFragmentBinding
    private var continueRolling: Boolean = false

    @Inject
    lateinit var viewModel: DiceGameViewModel

    private lateinit var game: ScreenGameModel

    companion object {
        private const val GAME_KEY = "GAME_KEY"

        fun newInstance(gameModel: ScreenGameModel): DiceGameFragment = DiceGameFragment().apply {
            arguments = Bundle().apply {
                putParcelable(GAME_KEY, gameModel)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            game = it.getParcelable(GAME_KEY) ?: ScreenGameModel("", "", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DiceGameFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DiceGameFragmentBinding.bind(view)
        setupViewModel()
        setupListeners()
        showGameInfo()
    }

    override fun onStop() {
        super.onStop()
        binding.rgBetTypesSet.ClearCheck()
    }

    private fun showGameInfo() {
        binding.tvDiceGameRollResultWin.text = getText(R.string.dice_game_roll_result_text_start)
        when (game.isFavorite) {
            true -> {
                binding.ivAddToFavourites.hide()
                binding.ivDelFromFavourites.show()
            }
            false -> {
                binding.ivAddToFavourites.show()
                binding.ivDelFromFavourites.hide()
            }
        }
    }


    private fun setupListeners() {
        ivAddToFavourites.setOnClickListener { changeGameFavorite() }
        ivDelFromFavourites.setOnClickListener { changeGameFavorite() }
        binding.ivBack.setOnClickListener { navigator.back() }
        binding.btnShowBetHistory.setOnClickListener { navigator.showDiceGameBetHistoryFragment() }
        binding.btnRoll.setOnClickListener {
            terminateDiceRollingAnimation()
            val id: Int = binding.rgBetTypesSet.GetCheckedRadioButtonId()
            val dateNow = Calendar.getInstance().time
            val sdf = SimpleDateFormat("dd.MM.yyyy hh:mm:ss", Locale.getDefault())
            val currentDateString = sdf.format(dateNow).toString()
            if (id != -1) {
                when (id) {
                    binding.radio1.id -> viewModel.placeBet(0, currentDateString)
                    binding.radio2.id -> viewModel.placeBet(1, currentDateString)
                    binding.radio3.id -> viewModel.placeBet(2, currentDateString)
                    binding.radio4.id -> viewModel.placeBet(3, currentDateString)
                    binding.radio5.id -> viewModel.placeBet(4, currentDateString)
                    binding.radio6.id -> viewModel.placeBet(5, currentDateString)
                    binding.radioEven.id -> viewModel.placeBet(6, currentDateString)
                    binding.radioOdd.id -> viewModel.placeBet(7, currentDateString)
                }
            } else {
                Toast
                    .makeText(activity, "Place Your Bet first!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun changeGameFavorite() {
        viewModel.changeGameFavorite(game)
        changeFavoriteStarView()
    }

    private fun changeFavoriteStarView() {
        if (ivAddToFavourites.isVisible()) {
            ivDelFromFavourites.show()
            ivAddToFavourites.hide()
        } else {
            ivDelFromFavourites.hide()
            ivAddToFavourites.show()
        }
    }

    private fun setupViewModel() {
        (requireActivity().application as Application).getComponent().inject(this)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.diceGameResultFragmentStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is DiceGameResultFragmentState.Loading -> {
                    showLoading()
                    GlobalScope.launch {
                        for (i in 0 until 20) {
                            if (continueRolling) doRoll()
                            else break
                            delay(50)
                        }
                    }
                }
                is DiceGameResultFragmentState.Error -> showError(it.errorMessage)
                is DiceGameResultFragmentState.Content -> showContent(it.gameResultApi)
            }
        }
        viewModel.changeGameFavoriteResultLiveData.observe(viewLifecycleOwner, { result ->
            if (!result.isError) {
                if (result.successResult) game.isFavorite = !game.isFavorite
            } else {
                handleErrorOnUi(result.errorResult)
                changeFavoriteStarView()
            }
        })
    }

    private fun showContent(gameResultApi: DiceGameResultModel) {
        terminateDiceRollingAnimation()
        var evenOrOdd = "Even"
        if (gameResultApi.droppedNumber % 2 != 0) evenOrOdd = "Odd"
        val stringGameResult = String.format(
            getString(R.string.dice_game_roll_result_text),
            gameResultApi.droppedNumber,
            evenOrOdd
        )
        with(binding) {
            tvErrorMessage.hide()
            if (gameResultApi.isBetWon) {
                tvDiceGameRollResultLose.visibility = View.INVISIBLE
                tvDiceGameRollResultWin.visibility = View.VISIBLE
                tvDiceGameRollResultWin.text = stringGameResult

            } else {
                tvDiceGameRollResultWin.visibility = View.INVISIBLE
                tvDiceGameRollResultLose.visibility = View.VISIBLE
                tvDiceGameRollResultLose.text = stringGameResult
            }
        }
        //set ivDiceImage
        getRandomDiceImageWithValueOnTop(gameResultApi.droppedNumber)

    }

    private fun handleErrorOnUi(errorType: GameScreenErrorType) {
        when (errorType) {
            GameScreenErrorType.NETWORK_ERROR -> navigator.showNoNetworkFragment()
            GameScreenErrorType.UNAUTHORIZED -> {
                navigator.showMainFragment()
                Toast.makeText(
                    context, getString(errorType.errorMessage), Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
                Toast.makeText(
                    context, getString(R.string.change_favorite_game_text), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showError(errorMessage: NetworkErrorType) {
        terminateDiceRollingAnimation()
        if (errorMessage == NetworkErrorType.NO_NETWORK) {
            navigator.showNoNetworkFragment()
        } else {
            with(binding) {
                tvDiceGameRollResultWin.hide()
                tvDiceGameRollResultLose.hide()
                tvErrorMessage.hide()
                tvErrorMessage.apply {
                    show()
                    text = errorMessage.name
                }
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            continueRolling = true
            tvDiceGameRollResultWin.text = ""
            tvDiceGameRollResultWin.show()
            tvDiceGameRollResultLose.hide()
            tvErrorMessage.hide()
            //anim
        }
    }

    private fun terminateDiceRollingAnimation() {
        continueRolling = false
    }

    private fun doRoll() { // only does a single roll
        getRandomDiceImageWithValueOnTop(Random.nextInt(6) + 1)
    }

    private fun getRandomDiceImageWithValueOnTop(valueOnTop: Int) {
        when (valueOnTop) {
            1 -> when (Random.nextInt(4) + 1) {
                1 -> setDiceImage(R.drawable.dice_1top_2left_3right)
                2 -> setDiceImage(R.drawable.dice_1top_3left_5right)
                3 -> setDiceImage(R.drawable.dice_1top_4left_2right)
                else -> setDiceImage(R.drawable.dice_1top_5left_4right)
            }
            2 -> when (Random.nextInt(4) + 1) {
                1 -> setDiceImage(R.drawable.dice_2top_1left_4right)
                2 -> setDiceImage(R.drawable.dice_2top_3left_1right)
                3 -> setDiceImage(R.drawable.dice_2top_4left_6right)
                else -> setDiceImage(R.drawable.dice_2top_6left_3right)
            }
            3 -> when (Random.nextInt(4) + 1) {
                1 -> setDiceImage(R.drawable.dice_3top_1left_2right)
                2 -> setDiceImage(R.drawable.dice_3top_2left_6right)
                3 -> setDiceImage(R.drawable.dice_3top_5left_1right)
                else -> setDiceImage(R.drawable.dice_3top_6left_5right)
            }
            4 -> when (Random.nextInt(4) + 1) {
                1 -> setDiceImage(R.drawable.dice_4top_1left_5right)
                2 -> setDiceImage(R.drawable.dice_4top_2left_1right)
                3 -> setDiceImage(R.drawable.dice_4top_5left_6right)
                else -> setDiceImage(R.drawable.dice_4top_6left_2right)
            }
            5 -> when (Random.nextInt(4) + 1) {
                1 -> setDiceImage(R.drawable.dice_5top_1left_3right)
                2 -> setDiceImage(R.drawable.dice_5top_3left_6right)
                3 -> setDiceImage(R.drawable.dice_5top_4left_1right)
                else -> setDiceImage(R.drawable.dice_5top_6left_4right)
            }
            else -> when (Random.nextInt(4) + 1) {
                1 -> setDiceImage(R.drawable.dice_6top_2left_4right)
                2 -> setDiceImage(R.drawable.dice_6top_3left_2right)
                3 -> setDiceImage(R.drawable.dice_6top_4left_5right)
                else -> setDiceImage(R.drawable.dice_6top_5left_3right)
            }
        }
    }

    private fun setDiceImage(drawableRes: Int) {
        binding.ivDiceImage.setImageDrawable(activity?.let {
            ContextCompat.getDrawable(
                it,
                drawableRes
            )
        })
    }
}

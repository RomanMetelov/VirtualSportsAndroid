package com.example.virtualsportsandroid.dices.game.data

import com.example.virtualsportsandroid.dices.BetType
import com.example.virtualsportsandroid.dices.DiceGameResultModel
import com.example.virtualsportsandroid.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.random.Random


@Suppress("EmptyClassBlock", "MagicNumber")
class DiceGameResultRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) {

    //написать тут поход на сервер
    //override suspend fun
    @Suppress("EmptyClassBlock", "MagicNumber")
    suspend fun getDiceGameResult(betTypeId: Int, datetime: String): Result<DiceGameResultModel, String> =
        withContext(dispatcher) {
            delay(1500)
            val diceGameResult: DiceGameResultModel
            val isBetWon: Boolean
            val diceBet: BetType = getBetType(betTypeId)
            val diceRollRandomResult: Int = getDiceRollRandomResult()
            isBetWon = isBetWon(diceBet, diceRollRandomResult)
            val dateNow = Calendar.getInstance().time
            val sdf = SimpleDateFormat("dd.MM.yyyy hh:mm:ss", Locale.getDefault())
            val currentDateString = sdf.format(dateNow).toString()
            diceGameResult = DiceGameResultModel(
                1.toString(),
                currentDateString,
                diceBet,
                diceRollRandomResult,
                isBetWon
            )
            return@withContext Result.success(diceGameResult)
        }

    private fun getBetType(betTypeId: Int): BetType {
        return when (betTypeId) {
            0 -> BetType.NUMBER1
            1 -> BetType.NUMBER2
            2 -> BetType.NUMBER3
            3 -> BetType.NUMBER4
            4 -> BetType.NUMBER5
            5 -> BetType.NUMBER6
            6 -> BetType.EVEN
            else -> BetType.ODD
        }
    }

    private fun getDiceRollRandomResult(): Int {
        return Random.nextInt(6) + 1
    }

    private fun isBetWon(betType: BetType, diceRollResult: Int): Boolean {
        return when (betType) {
            BetType.NUMBER1 -> diceRollResult == 1
            BetType.NUMBER2 -> diceRollResult == 2
            BetType.NUMBER3 -> diceRollResult == 3
            BetType.NUMBER4 -> diceRollResult == 4
            BetType.NUMBER5 -> diceRollResult == 5
            BetType.NUMBER6 -> diceRollResult == 6
            BetType.EVEN -> diceRollResult % 2 == 0
            else -> diceRollResult % 2 != 0
        }
    }
}

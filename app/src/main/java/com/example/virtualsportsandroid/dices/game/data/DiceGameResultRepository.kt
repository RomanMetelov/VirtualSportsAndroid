package com.example.virtualsportsandroid.dices.game.data

import com.example.virtualsportsandroid.dices.BetType
import com.example.virtualsportsandroid.dices.DiceGameResultModel
import com.example.virtualsportsandroid.utils.Result
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import com.example.virtualsportsandroid.utils.api.NetworkExceptionHandler
import com.example.virtualsportsandroid.utils.sharedPref.SharedPref
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.random.Random


@Suppress("EmptyClassBlock", "MagicNumber", "TooGenericExceptionCaught")
class DiceGameResultRepository @Inject constructor(
    private val diceGameResultService: DiceGameResultService,
    private val dispatcher: CoroutineDispatcher,
    private val networkExceptionHandler: NetworkExceptionHandler,
    private val sharedPref: SharedPref
) {
    @Suppress("EmptyClassBlock", "MagicNumber")
    suspend fun getDiceGameResult(
        datetime: String,
        betTypeId: Int
    ): Result<DiceGameResultModel, NetworkErrorType> =
        withContext(dispatcher) {
            val diceGameResult: DiceGameResultModel =
                diceGameResultService.getDiceGameResult(datetime, betTypeId)
            return@withContext try {
                Result.success(diceGameResult)
            } catch (e: Exception) {
                networkExceptionHandler.handleError(e)
            }
        }
}

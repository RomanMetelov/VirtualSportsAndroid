package com.example.virtualsportsandroid

import com.example.virtualsportsandroid.dices.game.data.DiceGameResultApiModel
import com.example.virtualsportsandroid.dices.game.domain.BetType
import com.example.virtualsportsandroid.dices.game.domain.DiceGameResultModel
import com.example.virtualsportsandroid.dices.game.domain.FromApiToUiMapper
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

class FromApiToUiMapperTest {

    @ExperimentalCoroutinesApi
    @Test
    fun `mapping with correct values works correctly`() {
        val id = "1"
        val datetime = "stringvalue"
        val betType = 2
        val droppedNumber = 6
        val isBetWon = false

        val mockkDiceGameResultApiModel = DiceGameResultApiModel(
            id = id,
            datetime = datetime,
            betType = betType,
            droppedNumber = droppedNumber,
            isBetWon = isBetWon
        )

        val mockkDiceGameResultUiModel = DiceGameResultModel(
            id = id,
            datetime = datetime,
            betType = BetType.valueOf((betType + 1).toString()),
            droppedNumber = droppedNumber,
            isBetWon = isBetWon
        )

        val fromApiToUiMapper = FromApiToUiMapper()

        runBlockingTest {
            fromApiToUiMapper.invoke(mockkDiceGameResultApiModel) shouldBe mockkDiceGameResultUiModel
        }
    }

}
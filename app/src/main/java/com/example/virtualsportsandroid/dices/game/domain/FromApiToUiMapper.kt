package com.example.virtualsportsandroid.dices.game.domain

import com.example.virtualsportsandroid.dices.game.data.DiceGameResultApiModel
import javax.inject.Inject

@Suppress("MagicNumber")
class FromApiToUiMapper @Inject constructor() {

    operator fun invoke(diceGameApiModel: DiceGameResultApiModel): DiceGameResultModel {
        val betType = when (diceGameApiModel.betType) {
            0 -> BetType.NUMBER1
            1 -> BetType.NUMBER2
            2 -> BetType.NUMBER3
            3 -> BetType.NUMBER4
            4 -> BetType.NUMBER5
            5 -> BetType.NUMBER6
            6 -> BetType.EVEN
            else -> BetType.ODD
        }
        return DiceGameResultModel(
            id = diceGameApiModel.id,
            datetime = diceGameApiModel.datetime,
            betType = betType,
            droppedNumber = diceGameApiModel.droppedNumber,
            isBetWon = diceGameApiModel.isBetWon
        )
    }

}

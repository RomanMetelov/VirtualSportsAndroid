package com.example.virtualsportsandroid.game.domain

import com.example.virtualsportsandroid.game.data.ScreenGameModel
import com.example.virtualsportsandroid.mainScreen.domain.model.GameModel
import javax.inject.Inject

@Suppress("ForbiddenComment")
class ScreenGameModelMapper @Inject constructor() {

    fun map(gameModel: GameModel): ScreenGameModel {
        return ScreenGameModel(
            id = gameModel.id,
            displayName = gameModel.displayName,
            url = ""
            //TODO: change to gameModel.url
        )
    }
}

package com.example.virtualsportsandroid.game.domain

import com.example.virtualsportsandroid.game.data.api.GameScreenRepository
import com.example.virtualsportsandroid.utils.Result
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import javax.inject.Inject

class PlayGameUseCase @Inject constructor(private val gameScreenRepository: GameScreenRepository) {

    suspend operator fun invoke(gameId: String): Result<Boolean, NetworkErrorType> {
        return gameScreenRepository.playGame(gameId)
    }

}

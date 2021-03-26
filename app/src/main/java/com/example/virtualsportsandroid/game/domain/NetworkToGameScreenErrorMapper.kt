package com.example.virtualsportsandroid.game.domain

import com.example.virtualsportsandroid.game.data.api.GameScreenErrorType
import com.example.virtualsportsandroid.utils.api.NetworkErrorType
import javax.inject.Inject

class NetworkToGameScreenErrorMapper @Inject constructor() {

    operator fun invoke(networkError: NetworkErrorType): GameScreenErrorType {
        return when (networkError) {
            NetworkErrorType.NO_NETWORK -> GameScreenErrorType.NETWORK_ERROR
            NetworkErrorType.UNAUTHORIZED -> GameScreenErrorType.UNAUTHORIZED
            else -> GameScreenErrorType.UNKNOWN_ERROR
        }
    }
}

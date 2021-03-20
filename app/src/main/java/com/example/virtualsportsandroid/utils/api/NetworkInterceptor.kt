package com.example.virtualsportsandroid.utils.api

import com.example.virtualsportsandroid.utils.Result
import javax.inject.Inject

class NetworkInterceptor @Inject constructor() {

    private companion object {
        private const val NO_NETWORK = "Unable to resolve host"
    }

    fun <T> getError(exception: Exception): Result<T, NetworkErrorType> {
        if (exception.message?.contains(NO_NETWORK, ignoreCase = true) == true)
            return Result.error(NetworkErrorType.NO_NETWORK)
        return when (exception.message) {
            NetworkStatusCode.NotFound.code.toString() -> Result.error(NetworkErrorType.RESOURCE_NOT_FOUND)
            NetworkStatusCode.Forbidden.code.toString() -> Result.error(NetworkErrorType.API_RATE_LIMIT_EXCEED)
            NetworkStatusCode.Unauthorized.code.toString() -> Result.error(NetworkErrorType.UNAUTHORIZED)
            else -> Result.error(NetworkErrorType.UNKNOWN_ERROR)
        }
    }
}

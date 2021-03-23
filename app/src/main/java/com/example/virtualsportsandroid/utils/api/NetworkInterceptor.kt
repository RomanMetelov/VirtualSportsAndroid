package com.example.virtualsportsandroid.utils.api

import com.example.virtualsportsandroid.utils.Result
import java.net.UnknownHostException
import javax.inject.Inject

class NetworkInterceptor @Inject constructor() {

    fun <T> getError(exception: Exception): Result<T, NetworkErrorType> {
        if (exception is UnknownHostException)
            return Result.error(NetworkErrorType.NO_NETWORK)
        return when (exception.message) {
            NetworkStatusCode.BadRequest.code.toString() -> Result.error(NetworkErrorType.BAD_REQUEST)
            NetworkStatusCode.NotFound.code.toString() -> Result.error(NetworkErrorType.RESOURCE_NOT_FOUND)
            NetworkStatusCode.Forbidden.code.toString() -> Result.error(NetworkErrorType.API_RATE_LIMIT_EXCEED)
            NetworkStatusCode.Unauthorized.code.toString() -> Result.error(NetworkErrorType.UNAUTHORIZED)
            NetworkStatusCode.Conflict.code.toString() -> Result.error(NetworkErrorType.USER_EXIST)
            else -> Result.error(NetworkErrorType.UNKNOWN_ERROR)
        }
    }

}

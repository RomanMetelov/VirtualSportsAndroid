package com.example.virtualsportsandroid.utils.api

import com.example.virtualsportsandroid.utils.Result
import java.net.UnknownHostException
import javax.inject.Inject

class NetworkInterceptor @Inject constructor() {

    fun <T> getError(exception: Exception): Result<T, NetworkErrorType> {
        if (exception is UnknownHostException)
            return Result.error(NetworkErrorType.NO_NETWORK)
        return when {
            exception.message?.contains(NetworkStatusCode.BadRequest.code.toString()) == true -> Result.error(
                NetworkErrorType.BAD_REQUEST
            )
            exception.message?.contains(NetworkStatusCode.NotFound.code.toString()) == true -> Result.error(
                NetworkErrorType.RESOURCE_NOT_FOUND
            )
            exception.message?.contains(NetworkStatusCode.Forbidden.code.toString()) == true -> Result.error(
                NetworkErrorType.API_RATE_LIMIT_EXCEED
            )
            exception.message?.contains(NetworkStatusCode.Unauthorized.code.toString()) == true -> Result.error(
                NetworkErrorType.UNAUTHORIZED
            )
            exception.message?.contains(NetworkStatusCode.Conflict.code.toString()) == true -> Result.error(
                NetworkErrorType.USER_EXIST
            )
            else -> Result.error(NetworkErrorType.UNKNOWN_ERROR)
        }
    }

}

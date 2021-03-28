package com.example.virtualsportsandroid.utils.api

@Suppress("MagicNumber")
enum class NetworkStatusCode(val code: Int) {
    BadRequest(400),
    Unauthorized(401),
    Forbidden(403),
    NotFound(404),
    Conflict(409),
}

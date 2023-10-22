package com.example.restaurant.data.repository

sealed class Response<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T) : Response<T>(data)
    class Error<T>(error: Throwable, data: T? = null) : Response<T>(data, error)
}
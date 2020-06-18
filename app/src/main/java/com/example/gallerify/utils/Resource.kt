package com.example.gallerify.utils

sealed class Resource<T>(data: T? = null, message: String? = null) {
    class Success<T>(data: T?, message: String? = null) : Resource<T>(data, message)
    class Error<T>(data: T? = null, message: String? = null) : Resource<T>(data, message)
    class Loading<T>() : Resource<T>()
}
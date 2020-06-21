package com.example.gallerify.utils.resources

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?, message: String? = null, val additionalData:String? = null) : Resource<T>(data, message)
    class Error<T>(data: T? = null, message: String? = null) : Resource<T>(data, message)
    class Loading<T>() : Resource<T>()
}
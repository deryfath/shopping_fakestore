package com.shopping.project.data.api

sealed class ResponseBody<out T> {
    data class Success<out T>(val data: T) : ResponseBody<T>()
    data class Error(val message: String) : ResponseBody<Nothing>()
}
package com.example.networkdemoapplication.remote

data class BaseResponse<T>(
    val code: Int,
    val message: String?,
    val data: T?
)

package com.example.networkdemoapplication.remote

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.example.networkdemoapplication.util.CONNECTION_ERROR

const val DEFAULT_ERROR_CODE = -1

@Keep
sealed class NetworkState<out T> {
    class Loading<out T> : NetworkState<T>()

    @Keep
    data class Success<out T>(
        @Keep
        val data: T?,
        val status: String?=null,
        val type: String?=null,
        val activity: String?=null,
        val message: String? = null,
        var code: Int? = null
    ) : NetworkState<T>()


    data class Error(
        val message: String? = null,
        val errCode: Int = DEFAULT_ERROR_CODE,
        val t: Throwable = Throwable(CONNECTION_ERROR),
        @StringRes val stringRes: Int? = null
    ) : NetworkState<Nothing>()

    data class Failure<out T>(val throwable: String?) : NetworkState<T>()
}

fun <T> NetworkState<T>.data(): T? {
    return when (this) {
        is NetworkState.Success -> this.data
        else -> null
    }
}
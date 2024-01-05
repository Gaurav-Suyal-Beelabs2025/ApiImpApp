package com.example.networkdemoapplication.util

import com.example.networkdemoapplication.model.ErrorModel
import com.example.networkdemoapplication.remote.NetworkState.Error
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketException
import java.util.regex.Pattern

suspend inline fun coroutineErrorHandler(t: Throwable): Error {
    return when (t) {
        is IOException -> Error(t = t)
        is HttpException -> {
            val code = t.code()
            val errBody = withContext(Dispatchers.IO) { t.response()?.errorBody()?.string() }
            if (isValidJson(errBody)) {
                val message = getMessage(convertErrorBody(errBody)) ?: CONNECTION_ERROR
                Error(message, code)
            } else {
                val htmlPattern = Pattern.compile(".*<[^>]+>.*", Pattern.DOTALL)
                val isHTML = htmlPattern.matcher(errBody ?: "").matches()
                val message = if (isHTML) CONNECTION_ERROR else errBody
                Error(message?.replace("\"", ""), code)
            }
        }
        is SocketException -> {
            Error(CONNECTION_ERROR)
        }
        else -> Error(t = Throwable(CONNECTION_ERROR))
    }
}

fun convertErrorBody(errBody: String?): ErrorModel? {
    return try {
        errBody?.fromJson<ErrorModel>()
    } catch (exception: Exception) {
        null
    }
}

fun getMessage(errorResponse: ErrorModel?): String? {
    val errors = errorResponse?.message
    return when {
        !errors.isNullOrEmpty() -> errors
        !errorResponse?.message.isNullOrEmpty() -> errorResponse?.message
        else -> null
    }
}
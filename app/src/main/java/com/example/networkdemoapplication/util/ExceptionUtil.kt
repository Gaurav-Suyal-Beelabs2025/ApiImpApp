package com.example.networkdemoapplication.util

import android.util.Log

object ExceptionUtil {
    fun errorMessage(method: String?, message: String?, e: Exception) {
        logException(e)
    }

    fun logException(e: Exception) {
        Log.e(e.javaClass.name, e.message, e.cause)
        e.printStackTrace()
    }

    private fun sendExceptionEmail(method: String, message: String, e: Exception) {
        //TODO: Implement send exception logic here and include
    }
}

package com.example.networkdemoapplication.util

const val CONNECTION_ERROR =
    "Failed to connect. Make sure you have an active internet connection."
var token = ""
const val register = "REGISTER"

class Constant {
    companion object {
        const val CONNECTION_TIMEOUT: Long = 2
        const val READ_TIMEOUT: Long = 2
        const val WRITE_TIMEOUT: Long = 2
        const val BASEURL = "http://43.204.89.10:4052/v1/"
        const val TOKEN = "Token"
    }
}
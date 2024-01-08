package com.example.networkdemoapplication.views.auth.data

    data class DeviceAuthData(
    val code: Number?,
    val message: Any?,
    val `data`: Data?,
    val requestedAt: String?,
    val responseTime: String?
)

data class Data(
    val deviceId: String?,
    val token: String?,
    val id: String?
)

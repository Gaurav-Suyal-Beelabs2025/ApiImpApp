package com.example.networkdemoapplication.views.auth.data

data class LoginData(
    val code: Number?,
    val message: String?,
    val `data`: VerifiedData?,
    val requestedAt: String?,
    val responseTime: String?
)


data class User(
    val stores: List<Any>?,
    val documents: List<Any>?,
    val zone: List<Any>?,
    val status: String?,
    val mobile: String?,
    val deviceId: String?,
    val wallet: Number?,
    val cashback: Number?,
    val lockedWallet: Number?,
    val categories: List<Any>?,
    val chargesType: String?,
    val pancardVerified: Boolean?,
    val platform: String?,
    val id: String?
)

data class Access(
    val token: String?,
    val expires: String?
)

data class Refresh(
    val token: String?,
    val expires: String?
)

data class Tokens(
    val access: Access?,
    val refresh: Refresh?
)

data class VerifiedData(
    val user: User?,
    val tokens: Tokens?
)

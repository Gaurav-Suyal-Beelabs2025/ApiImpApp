package com.example.networkdemoapplication.remote

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class ApiResponse<T>(
    @SerializedName("data")
    @Expose
    var data: T? = null,

    @SerializedName("status")
    @Expose
    val status: String? = null,

    @SerializedName("message")
    @Expose
    val message: String? = null,

    @SerializedName("type")
    @Expose
    val type: String? = null,

    @SerializedName("activity")
    @Expose
    val activity: String? = null,

    @SerializedName("code")
    @Expose
    val code: Int? = null
)
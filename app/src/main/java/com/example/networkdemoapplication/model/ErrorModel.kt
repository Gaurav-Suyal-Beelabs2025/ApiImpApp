package com.example.networkdemoapplication.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ErrorModel(
    @SerializedName("code")
    @Expose
    var code: Int,

    @SerializedName("message")
    @Expose
    var message: String)
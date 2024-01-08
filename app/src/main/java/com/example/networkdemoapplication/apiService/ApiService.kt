package com.example.networkdemoapplication.apiService

import com.example.networkdemoapplication.model.TestData
import com.example.networkdemoapplication.remote.ApiResponse
import com.example.networkdemoapplication.remote.BaseResponse
import com.example.networkdemoapplication.views.auth.data.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiService {
    @POST("auth/device")
    suspend fun authDevice(@Body authDeviceModel: AuthDeviceModel): ApiResponse<DeviceAuthData>

    @POST("auth/send-otp")
    suspend fun sendOtp(@Body mobileNumberModel: MobileNumberModel): ApiResponse<SendOtpData>

    @POST("auth/verify-otp")
    suspend fun verifyOtp(@Body verifyOtpModel: VerifyOtpModel): ApiResponse<LoginData>

    @GET("activity")
    suspend fun test(): Response<BaseResponse<TestData>>



}
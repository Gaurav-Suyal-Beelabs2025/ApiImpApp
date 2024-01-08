package com.example.networkdemoapplication.apiService

import com.example.networkdemoapplication.model.TestData
import com.example.networkdemoapplication.remote.ApiResponse
import com.example.networkdemoapplication.remote.BaseResponse
import com.example.networkdemoapplication.views.auth.data.AuthDeviceModel
import com.example.networkdemoapplication.views.auth.data.DeviceAuthData
import com.example.networkdemoapplication.views.auth.data.MobileNumberModel
import com.example.networkdemoapplication.views.auth.data.SendOtpData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {
    @POST("auth/device")
    suspend fun authDevice(@Body authDeviceModel: AuthDeviceModel): ApiResponse<DeviceAuthData>

    @POST("auth/send-otp")
    suspend fun sendOtp(@Body mobileNumberModel: MobileNumberModel ): ApiResponse<SendOtpData>

    @GET("activity")
    suspend fun test(): Response<BaseResponse<TestData>>



}
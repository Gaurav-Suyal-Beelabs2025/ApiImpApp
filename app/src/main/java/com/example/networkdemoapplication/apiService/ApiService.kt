package com.example.networkdemoapplication.apiService

import com.example.networkdemoapplication.model.TestData
import com.example.networkdemoapplication.remote.ApiResponse
import com.example.networkdemoapplication.remote.BaseResponse
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {
//    @POST("api/UserRegister")
//    suspend fun registration(@Body registrationModel: RegistrationModel): ApiResponse<LogInData>

    @GET("activity")
    suspend fun test(): Response<BaseResponse<TestData>>



}
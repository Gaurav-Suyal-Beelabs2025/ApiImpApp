package com.example.networkdemoapplication.dagger

import com.example.networkdemoapplication.util.Constant
import com.example.networkdemoapplication.util.SharedPref
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()


        if (SharedPref.keyExists(Constant.TOKEN)) {
            val token = SharedPref.read(Constant.TOKEN, null)
            if (token != null) {
                builder.header("token", token)
            }
        }
        return chain.proceed(builder.build())
    }
}

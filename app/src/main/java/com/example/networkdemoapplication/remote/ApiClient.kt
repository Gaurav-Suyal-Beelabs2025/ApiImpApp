package com.example.networkdemoapplication.remote

import android.util.Log
import androidx.databinding.ktx.BuildConfig
import com.example.networkdemoapplication.util.Constant
import com.google.gson.GsonBuilder
import com.example.networkdemoapplication.util.SharedPref
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(Constant.BASEURL)
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().serializeNulls().create()
                    )
                )
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(getOkHttpBuilder().build())
                .build()
        }


        private fun getOkHttpBuilder(timeout: Int = 120): OkHttpClient.Builder {
            var token = ""
            val builder = OkHttpClient.Builder()
            val dispatcher = Dispatcher()
            builder.dispatcher(dispatcher)
            builder.connectTimeout(timeout.toLong(), TimeUnit.SECONDS)
            builder.readTimeout(timeout.toLong(), TimeUnit.SECONDS)
            builder.writeTimeout(timeout.toLong(), TimeUnit.SECONDS)
            if (SharedPref.keyExists(Constant.TOKEN)) {
                token = SharedPref.read(Constant.TOKEN, null).toString()
                Log.d("token value", token)
            }else{
                Log.d("token is empty", token)
            }

            builder.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Authorization", token)
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }

            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(interceptor)
            }

            return builder
        }
    }
}
package com.example.networkdemoapplication.dagger

import dagger.Binds
import dagger.Module
import okhttp3.Interceptor

@Module
abstract class AuthenticationModule() {
    @Binds
    abstract fun bindAuthInterceptor(authInterceptor: AuthenticationInterceptor): Interceptor
}
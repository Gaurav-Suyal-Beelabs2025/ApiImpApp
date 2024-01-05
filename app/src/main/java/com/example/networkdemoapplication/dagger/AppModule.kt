package com.example.networkdemoapplication.dagger


import dagger.Module as Module1

@Module1(
    includes = [
        NetworkModule::class,
        AuthenticationModule::class,
        ViewModelsModule::class
    ]
)
class AppModule {


}
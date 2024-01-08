package com.example.networkdemoapplication.dagger


import com.example.networkdemoapplication.views.auth.MobileNumberFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun addMobileNumberFragment(): MobileNumberFragment

}
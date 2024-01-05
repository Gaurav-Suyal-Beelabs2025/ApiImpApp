package com.example.networkdemoapplication.dagger

import androidx.lifecycle.ViewModel
import com.example.networkdemoapplication.viewModel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(TestViewModel::class)
    abstract fun bindTestViewModal(viewModel: TestViewModel): ViewModel


}
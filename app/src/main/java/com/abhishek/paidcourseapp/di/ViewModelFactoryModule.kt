package com.abhishek.paidcourseapp.di

import androidx.lifecycle.ViewModelProvider
import com.abhishek.paidcourseapp.viewmodels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

/**
 * Created by Abhishek Kumar on 28/07/20.
 * (c)2020 VMock. All rights reserved.
 */

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}
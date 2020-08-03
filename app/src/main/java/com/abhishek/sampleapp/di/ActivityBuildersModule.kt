package com.abhishek.sampleapp.di

import com.abhishek.sampleapp.di.auth.AuthFragmentBuildersModule
import com.abhishek.sampleapp.di.auth.AuthModule
import com.abhishek.sampleapp.di.auth.AuthScope
import com.abhishek.sampleapp.di.auth.AuthViewModelModule
import com.abhishek.sampleapp.di.main.MainFragmentBuildersModule
import com.abhishek.sampleapp.di.main.MainModule
import com.abhishek.sampleapp.di.main.MainScope
import com.abhishek.sampleapp.di.main.MainViewModelModule
import com.abhishek.sampleapp.ui.auth.AuthActivity
import com.abhishek.sampleapp.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Abhishek Kumar on 28/07/20.
 * (c)2020 VMock. All rights reserved.
 */

@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
        modules = [AuthModule::class, AuthFragmentBuildersModule::class, AuthViewModelModule::class]
    )
    abstract fun contributeAuthActivity(): AuthActivity

    @MainScope
    @ContributesAndroidInjector(
        modules = [MainModule::class, MainFragmentBuildersModule::class, MainViewModelModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity
}
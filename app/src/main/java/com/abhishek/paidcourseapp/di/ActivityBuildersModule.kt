package com.abhishek.paidcourseapp.di

import com.abhishek.paidcourseapp.di.auth.AuthFragmentBuildersModule
import com.abhishek.paidcourseapp.di.auth.AuthModule
import com.abhishek.paidcourseapp.di.auth.AuthScope
import com.abhishek.paidcourseapp.di.auth.AuthViewModelModule
import com.abhishek.paidcourseapp.ui.auth.AuthActivity
import com.abhishek.paidcourseapp.ui.main.MainActivity
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

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}
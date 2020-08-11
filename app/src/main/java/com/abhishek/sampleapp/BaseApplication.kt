package com.abhishek.sampleapp

import android.app.Activity
import android.app.Application
import com.abhishek.sampleapp.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by Abhishek Kumar on 28/07/20.
 * (c)2020 VMock. All rights reserved.
 */

class BaseApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}
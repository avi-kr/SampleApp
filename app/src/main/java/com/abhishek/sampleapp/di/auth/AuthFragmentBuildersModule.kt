package com.abhishek.sampleapp.di.auth

import com.abhishek.sampleapp.ui.auth.ForgotPasswordFragment
import com.abhishek.sampleapp.ui.auth.LauncherFragment
import com.abhishek.sampleapp.ui.auth.LoginFragment
import com.abhishek.sampleapp.ui.auth.RegisterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Abhishek Kumar on 28/07/20.
 * (c)2020 VMock. All rights reserved.
 */

@Module
abstract class AuthFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeLauncherFragment(): LauncherFragment

    @ContributesAndroidInjector()
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector()
    abstract fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector()
    abstract fun contributeForgotPasswordFragment(): ForgotPasswordFragment
}
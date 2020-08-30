package com.abhishek.sampleapp.di

import android.app.Application
import com.abhishek.sampleapp.di.auth.AuthComponent
import com.abhishek.sampleapp.di.main.MainComponent
import com.abhishek.sampleapp.session.SessionManager
import com.abhishek.sampleapp.ui.BaseActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Abhishek Kumar on 28/07/20.
 * (c)2020 VMock. All rights reserved.
 */

@Singleton
@Component(
    modules = [
        AppModule::class,
        SubComponentsModule::class
    ]
)
interface AppComponent {

    val sessionManager: SessionManager

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(baseActivity: BaseActivity)

    fun authComponent(): AuthComponent.Factory

    fun mainComponent(): MainComponent.Factory
}
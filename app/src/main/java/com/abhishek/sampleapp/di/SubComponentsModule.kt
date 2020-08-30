package com.abhishek.sampleapp.di

import com.abhishek.sampleapp.di.auth.AuthComponent
import com.abhishek.sampleapp.di.main.MainComponent
import dagger.Module

/**
 * Created by Abhishek Kumar on 30/08/20.
 * (c)2020 VMock. All rights reserved.
 */

@Module(
    subcomponents = [
        AuthComponent::class,
        MainComponent::class
    ]
)
class SubComponentsModule
package com.abhishek.sampleapp.di.auth

import javax.inject.Scope

/**
 * Created by Abhishek Kumar on 28/07/20.
 * (c)2020 VMock. All rights reserved.
 *
 * AuthScope is strictly for login and registration
 */

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class AuthScope
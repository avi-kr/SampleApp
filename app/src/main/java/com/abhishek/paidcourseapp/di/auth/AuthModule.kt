package com.abhishek.paidcourseapp.di.auth

import com.abhishek.paidcourseapp.api.auth.OpenApiAuthService
import com.abhishek.paidcourseapp.persistence.AccountPropertiesDao
import com.abhishek.paidcourseapp.persistence.AuthTokenDao
import com.abhishek.paidcourseapp.repository.AuthRepository
import com.abhishek.paidcourseapp.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by Abhishek Kumar on 28/07/20.
 * (c)2020 VMock. All rights reserved.
 */

@Module
class AuthModule {

    // TEMPORARY
    @AuthScope
    @Provides
    fun provideFakeApiService(retrofitBuilder: Retrofit.Builder): OpenApiAuthService {
        return retrofitBuilder
            .build()
            .create(OpenApiAuthService::class.java)
    }

    @AuthScope
    @Provides
    fun provideAuthRepository(
        sessionManager: SessionManager,
        authTokenDao: AuthTokenDao,
        accountPropertiesDao: AccountPropertiesDao,
        openApiAuthService: OpenApiAuthService
    ): AuthRepository {
        return AuthRepository(
            authTokenDao,
            accountPropertiesDao,
            openApiAuthService,
            sessionManager
        )
    }
}
package com.abhishek.sampleapp.di.auth

import com.abhishek.sampleapp.api.auth.OpenApiAuthService
import com.abhishek.sampleapp.persistence.AccountPropertiesDao
import com.abhishek.sampleapp.persistence.AuthTokenDao
import com.abhishek.sampleapp.repository.auth.AuthRepository
import com.abhishek.sampleapp.session.SessionManager
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
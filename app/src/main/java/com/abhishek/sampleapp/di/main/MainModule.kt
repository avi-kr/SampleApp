package com.abhishek.sampleapp.di.main

import com.abhishek.sampleapp.api.main.OpenApiMainService
import com.abhishek.sampleapp.persistence.AccountPropertiesDao
import com.abhishek.sampleapp.persistence.AppDatabase
import com.abhishek.sampleapp.persistence.BlogPostDao
import com.abhishek.sampleapp.repository.main.AccountRepository
import com.abhishek.sampleapp.repository.main.BlogRepository
import com.abhishek.sampleapp.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by Abhishek Kumar on 03/08/20.
 * (c)2020 VMock. All rights reserved.
 */

@Module
class MainModule {

    @MainScope
    @Provides
    fun provideOpenApiMainService(retrofitBuilder: Retrofit.Builder): OpenApiMainService {
        return retrofitBuilder
            .build()
            .create(OpenApiMainService::class.java)
    }

    @MainScope
    @Provides
    fun provideAccountRepository(
        openApiMainService: OpenApiMainService,
        accountPropertiesDao: AccountPropertiesDao,
        sessionManager: SessionManager
    ): AccountRepository {
        return AccountRepository(
            openApiMainService,
            accountPropertiesDao,
            sessionManager
        )
    }

    @MainScope
    @Provides
    fun provideBlogPostDao(db: AppDatabase): BlogPostDao {
        return db.getBlogPostDao()
    }

    @MainScope
    @Provides
    fun provideBlogRepository(
        openApiMainService: OpenApiMainService,
        blogPostDao: BlogPostDao,
        sessionManager: SessionManager
    ): BlogRepository {
        return BlogRepository(openApiMainService, blogPostDao, sessionManager)
    }
}
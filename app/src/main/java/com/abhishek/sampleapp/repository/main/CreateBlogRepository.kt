package com.abhishek.sampleapp.repository.main

import com.abhishek.sampleapp.api.main.OpenApiMainService
import com.abhishek.sampleapp.persistence.BlogPostDao
import com.abhishek.sampleapp.repository.JobManager
import com.abhishek.sampleapp.session.SessionManager
import javax.inject.Inject

/**
 * Created by Abhishek Kumar on 11/08/20.
 * (c)2020 VMock. All rights reserved.
 */

class CreateBlogRepository
@Inject
constructor(
    val openApiMainService: OpenApiMainService,
    val blogPostDao: BlogPostDao,
    val sessionManager: SessionManager
) : JobManager("CreateBlogRepository") {

    private val TAG: String = "AppDebug"
}
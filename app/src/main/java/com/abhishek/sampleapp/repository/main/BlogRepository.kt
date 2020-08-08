package com.abhishek.sampleapp.repository.main

import com.abhishek.sampleapp.api.main.OpenApiMainService
import com.abhishek.sampleapp.persistence.BlogPostDao
import com.abhishek.sampleapp.repository.JobManager
import com.abhishek.sampleapp.session.SessionManager
import javax.inject.Inject

/**
 * Created by Abhishek Kumar on 08/08/20.
 * (c)2020 VMock. All rights reserved.
 */

class BlogRepository
@Inject
constructor(
    val openApiMainService: OpenApiMainService,
    val blogPostDao: BlogPostDao,
    val sessionManager: SessionManager
) : JobManager("BlogRepository") {

    private val TAG: String = "AppDebug"


}
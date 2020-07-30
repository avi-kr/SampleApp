package com.abhishek.paidcourseapp.repository

import com.abhishek.paidcourseapp.api.auth.OpenApiAuthService
import com.abhishek.paidcourseapp.persistence.AccountPropertiesDao
import com.abhishek.paidcourseapp.persistence.AuthTokenDao
import com.abhishek.paidcourseapp.session.SessionManager
import javax.inject.Inject

/**
 * Created by Abhishek Kumar on 27/07/20.
 * (c)2020 VMock. All rights reserved.
 */

class AuthRepository
@Inject
constructor(
    val authTokenDao: AuthTokenDao,
    val accountPropertiesDao: AccountPropertiesDao,
    val openApiAuthService: OpenApiAuthService,
    val sessionManager: SessionManager
) {

}
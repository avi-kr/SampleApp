package com.abhishek.paidcourseapp.repository

import com.abhishek.paidcourseapp.api.auth.OpenApiAuthServer
import com.abhishek.paidcourseapp.persistence.AccountPropertiesDao
import com.abhishek.paidcourseapp.persistence.AuthTokenDao
import com.abhishek.paidcourseapp.session.SessionManager

/**
 * Created by Abhishek Kumar on 27/07/20.
 * (c)2020 VMock. All rights reserved.
 */

class AuthRepository
constructor(
    val authTokenDao: AuthTokenDao,
    val accountPropertiesDao: AccountPropertiesDao,
    val openApiAuthServer: OpenApiAuthServer,
    val sessionManager: SessionManager
) {

}
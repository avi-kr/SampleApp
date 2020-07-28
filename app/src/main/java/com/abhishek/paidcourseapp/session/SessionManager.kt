package com.abhishek.paidcourseapp.session

import android.app.Application
import com.abhishek.paidcourseapp.persistence.AuthTokenDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Abhishek Kumar on 27/07/20.
 * (c)2020 VMock. All rights reserved.
 */

@Singleton
class SessionManager
@Inject
constructor(
    val authTokenDao: AuthTokenDao,
    val application: Application
) {

}
package com.abhishek.paidcourseapp.session

import android.app.Application
import com.abhishek.paidcourseapp.persistence.AuthTokenDao

/**
 * Created by Abhishek Kumar on 27/07/20.
 * (c)2020 VMock. All rights reserved.
 */

class SessionManager
constructor(
    val authTokenDao: AuthTokenDao,
    val application: Application
) {

}
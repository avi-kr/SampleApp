package com.abhishek.paidcourseapp.ui

import com.abhishek.paidcourseapp.session.SessionManager
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Created by Abhishek Kumar on 28/07/20.
 * (c)2020 VMock. All rights reserved.
 */

abstract class BaseActivity : DaggerAppCompatActivity() {

    val TAG: String = "AppDebug"

    @Inject
    lateinit var sessionManager: SessionManager


}
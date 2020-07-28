package com.abhishek.paidcourseapp.ui.auth

import android.os.Bundle
import com.abhishek.paidcourseapp.R.layout
import com.abhishek.paidcourseapp.ui.BaseActivity

class AuthActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_auth)
    }
}
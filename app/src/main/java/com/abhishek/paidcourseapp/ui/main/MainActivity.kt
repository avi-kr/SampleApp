package com.abhishek.paidcourseapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.abhishek.paidcourseapp.R
import com.abhishek.paidcourseapp.ui.BaseActivity
import com.abhishek.paidcourseapp.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.activity_main.tool_bar

/**
 * Created by Abhishek Kumar on 29/07/20.
 * (c)2020 VMock. All rights reserved.
 */

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribeObservers()
    }

    fun subscribeObservers() {
        sessionManager.cachedToken.observe(this, Observer { authToken ->
            Log.d(TAG, "MainActivity, subscribeObservers: ViewState: ${authToken}")
            if (authToken == null || authToken.account_pk == -1 || authToken.token == null) {
                navAuthActivity()
                finish()
            }
        })
    }

    private fun navAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}

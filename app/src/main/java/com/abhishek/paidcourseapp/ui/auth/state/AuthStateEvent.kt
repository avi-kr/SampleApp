package com.abhishek.paidcourseapp.ui.auth.state

/**
 * Created by Abhishek Kumar on 29/07/20.
 * (c)2020 VMock. All rights reserved.
 */

sealed class AuthStateEvent {

    data class LoginAttemptEvent(
        val email: String,
        val password: String
    ) : AuthStateEvent()

    data class RegisterAttemptEvent(
        val email: String,
        val username: String,
        val password: String,
        val confirm_password: String
    ) : AuthStateEvent()

    class checkPreviousAuthEvent : AuthStateEvent()
}
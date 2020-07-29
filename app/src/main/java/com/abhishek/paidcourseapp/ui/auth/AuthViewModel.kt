package com.abhishek.paidcourseapp.ui.auth

import androidx.lifecycle.LiveData
import com.abhishek.paidcourseapp.models.AuthToken
import com.abhishek.paidcourseapp.repository.AuthRepository
import com.abhishek.paidcourseapp.ui.BaseViewModel
import com.abhishek.paidcourseapp.ui.DataState
import com.abhishek.paidcourseapp.ui.auth.state.AuthStateEvent
import com.abhishek.paidcourseapp.ui.auth.state.AuthStateEvent.LoginAttemptEvent
import com.abhishek.paidcourseapp.ui.auth.state.AuthStateEvent.RegisterAttemptEvent
import com.abhishek.paidcourseapp.ui.auth.state.AuthStateEvent.checkPreviousAuthEvent
import com.abhishek.paidcourseapp.ui.auth.state.AuthViewState
import com.abhishek.paidcourseapp.ui.auth.state.LoginFields
import com.abhishek.paidcourseapp.ui.auth.state.RegistrationFields
import com.abhishek.paidcourseapp.util.AbsentLiveData
import javax.inject.Inject

/**
 * Created by Abhishek Kumar on 27/07/20.
 * (c)2020 VMock. All rights reserved.
 */

class AuthViewModel
@Inject
constructor(
    val authRepository: AuthRepository
) : BaseViewModel<AuthStateEvent, AuthViewState>() {

    override fun initNewViewState(): AuthViewState {
        return AuthViewState()
    }

    fun setRegistrationnnFields(registrationFields: RegistrationFields) {
        val update = getCurrentViewStateOrNew()
        if (update.registrationFields == registrationFields) {
            return
        }
        update.registrationFields = registrationFields
        _viewState.value = update
    }

    fun setLoginFields(loginFields: LoginFields) {
        val update = getCurrentViewStateOrNew()
        if (update.loginFields == loginFields) {
            return
        }
        update.loginFields = loginFields
        _viewState.value = update
    }

    fun setAuthToken(authToken: AuthToken) {
        val update = getCurrentViewStateOrNew()
        if (update.authToken == authToken) {
            return
        }
        update.authToken = authToken
        _viewState.value = update
    }

    override fun handleStateEvent(stateEvent: AuthStateEvent): LiveData<DataState<AuthViewState>> {
        when(stateEvent) {
            is LoginAttemptEvent  -> {
                return AbsentLiveData.create()
            }
            is RegisterAttemptEvent -> {
                return AbsentLiveData.create()
            }
            is checkPreviousAuthEvent -> {
                return AbsentLiveData.create()
            }
        }
    }
}
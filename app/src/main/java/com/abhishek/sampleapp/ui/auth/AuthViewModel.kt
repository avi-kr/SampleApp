package com.abhishek.sampleapp.ui.auth

import androidx.lifecycle.LiveData
import com.abhishek.sampleapp.models.AuthToken
import com.abhishek.sampleapp.repository.auth.AuthRepository
import com.abhishek.sampleapp.ui.BaseViewModel
import com.abhishek.sampleapp.ui.DataState
import com.abhishek.sampleapp.ui.auth.state.AuthStateEvent
import com.abhishek.sampleapp.ui.auth.state.AuthStateEvent.LoginAttemptEvent
import com.abhishek.sampleapp.ui.auth.state.AuthStateEvent.None
import com.abhishek.sampleapp.ui.auth.state.AuthStateEvent.RegisterAttemptEvent
import com.abhishek.sampleapp.ui.auth.state.AuthStateEvent.CheckPreviousAuthEvent
import com.abhishek.sampleapp.ui.auth.state.AuthViewState
import com.abhishek.sampleapp.ui.auth.state.LoginFields
import com.abhishek.sampleapp.ui.auth.state.RegistrationFields
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

    override fun handleStateEvent(stateEvent: AuthStateEvent): LiveData<DataState<AuthViewState>> {
        when (stateEvent) {
            is LoginAttemptEvent -> {
                return authRepository.attemptLogin(
                    stateEvent.email,
                    stateEvent.password
                )
            }
            is RegisterAttemptEvent -> {
                return authRepository.attemptRegistration(
                    stateEvent.email,
                    stateEvent.username,
                    stateEvent.password,
                    stateEvent.confirm_password
                )
            }
            is CheckPreviousAuthEvent -> {
                return authRepository.checkPreviousAuthUser()
            }
            is None -> {
                return object : LiveData<DataState<AuthViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        value = DataState.data(null, null)
                    }
                }
            }
        }
    }

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

    fun cancelActiveJobs() {
        handlePendingData()
        authRepository.cancelActiveJobs()
    }

    fun handlePendingData() {
        setStateEvent(None())
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}
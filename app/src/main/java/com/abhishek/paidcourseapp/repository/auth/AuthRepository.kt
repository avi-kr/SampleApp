package com.abhishek.paidcourseapp.repository.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.abhishek.paidcourseapp.api.auth.OpenApiAuthService
import com.abhishek.paidcourseapp.models.AuthToken
import com.abhishek.paidcourseapp.persistence.AccountPropertiesDao
import com.abhishek.paidcourseapp.persistence.AuthTokenDao
import com.abhishek.paidcourseapp.session.SessionManager
import com.abhishek.paidcourseapp.ui.DataState
import com.abhishek.paidcourseapp.ui.Response
import com.abhishek.paidcourseapp.ui.ResponseType
import com.abhishek.paidcourseapp.ui.auth.state.AuthViewState
import com.abhishek.paidcourseapp.util.ApiEmptyResponse
import com.abhishek.paidcourseapp.util.ApiErrorResponse
import com.abhishek.paidcourseapp.util.ApiSuccessResponse
import com.abhishek.paidcourseapp.util.ErrorHandling.Companion.ERROR_UNKNOWN

/**
 * Created by Abhishek Kumar on 27/07/20.
 * (c)2020 VMock. All rights reserved.
 */

class AuthRepository
constructor(
    val authTokenDao: AuthTokenDao,
    val accountPropertiesDao: AccountPropertiesDao,
    val openApiAuthService: OpenApiAuthService,
    val sessionManager: SessionManager
) {

    fun attemptLogin(email: String, password: String): LiveData<DataState<AuthViewState>> {

        return openApiAuthService.login(email, password)
            .switchMap { response ->
                object : LiveData<DataState<AuthViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        when (response) {
                            is ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = AuthViewState(
                                        authToken = AuthToken(
                                            response.body.pk,
                                            response.body.token
                                        )
                                    ),
                                    response = null
                                )
                            }
                            is ApiErrorResponse -> {
                                value = DataState.error(
                                    response = Response(
                                        message = response.errorMessage,
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }
                            is ApiEmptyResponse -> {
                                value = DataState.error(
                                    response = Response(
                                        message = ERROR_UNKNOWN,
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }
                        }
                    }
                }
            }
    }

    fun attemptRegistration(
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): LiveData<DataState<AuthViewState>> {

        return openApiAuthService.register(email, username, password, confirmPassword)
            .switchMap { response ->
                object : LiveData<DataState<AuthViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        when (response) {
                            is ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = AuthViewState(
                                        authToken = AuthToken(
                                            response.body.pk,
                                            response.body.token
                                        )
                                    ),
                                    response = null
                                )
                            }
                            is ApiErrorResponse -> {
                                value = DataState.error(
                                    response = Response(
                                        message = response.errorMessage,
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }
                            is ApiEmptyResponse -> {
                                value = DataState.error(
                                    response = Response(
                                        message = ERROR_UNKNOWN,
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }
                        }
                    }
                }
            }
    }
}
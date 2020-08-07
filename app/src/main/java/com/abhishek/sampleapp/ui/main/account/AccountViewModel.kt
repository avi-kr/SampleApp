package com.abhishek.sampleapp.ui.main.account

import androidx.lifecycle.LiveData
import com.abhishek.sampleapp.models.AccountProperties
import com.abhishek.sampleapp.repository.main.AccountRepository
import com.abhishek.sampleapp.session.SessionManager
import com.abhishek.sampleapp.ui.BaseViewModel
import com.abhishek.sampleapp.ui.DataState
import com.abhishek.sampleapp.ui.main.account.state.AccountStateEvent
import com.abhishek.sampleapp.ui.main.account.state.AccountStateEvent.ChangePasswordEvent
import com.abhishek.sampleapp.ui.main.account.state.AccountStateEvent.GetAccountPropertiesEvent
import com.abhishek.sampleapp.ui.main.account.state.AccountStateEvent.None
import com.abhishek.sampleapp.ui.main.account.state.AccountStateEvent.UpdateAccountPropertiesEvent
import com.abhishek.sampleapp.ui.main.account.state.AccountViewState
import com.abhishek.sampleapp.util.AbsentLiveData
import javax.inject.Inject

/**
 * Created by Abhishek Kumar on 06/08/20.
 * (c)2020 VMock. All rights reserved.
 */

class AccountViewModel
@Inject
constructor(
    val sessionManager: SessionManager,
    val accountRepository: AccountRepository
) : BaseViewModel<AccountStateEvent, AccountViewState>() {

    override fun handleStateEvent(stateEvent: AccountStateEvent): LiveData<DataState<AccountViewState>> {
        when (stateEvent) {
            is GetAccountPropertiesEvent -> {
                return sessionManager.cachedToken.value?.let { authToken ->
                    accountRepository.getAccountProperties(authToken)
                } ?: AbsentLiveData.create()
            }
            is UpdateAccountPropertiesEvent -> {
                return sessionManager.cachedToken.value?.let { authToken ->
                    authToken.account_pk?.let { pk ->
                        accountRepository.saveAccountProperties(
                            authToken,
                            AccountProperties(
                                pk,
                                stateEvent.email,
                                stateEvent.userName
                            )
                        )
                    }
                } ?: AbsentLiveData.create()
            }
            is ChangePasswordEvent -> {
                return sessionManager.cachedToken.value?.let { authToken ->
                    accountRepository.updatePassword(
                        authToken,
                        stateEvent.currentPassword,
                        stateEvent.newPassword,
                        stateEvent.confirmNewPassword
                    )
                } ?: AbsentLiveData.create()
            }
            is None -> {
                return AbsentLiveData.create()
            }
        }
    }

    override fun initNewViewState(): AccountViewState {
        return AccountViewState()
    }

    fun setAccountPropertiesData(accountProperties: AccountProperties) {
        val update = getCurrentViewStateOrNew()
        if (update.accountProperties == accountProperties) {
            return
        }
        update.accountProperties = accountProperties
        _viewState.value = update
    }

    fun logout() {
        sessionManager.logout()
    }

    fun cancelActiveJobs() {
        handlePendingData()
        accountRepository.cancelActiveJobs()
    }

    fun handlePendingData() {
        setStateEvent(None())
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}
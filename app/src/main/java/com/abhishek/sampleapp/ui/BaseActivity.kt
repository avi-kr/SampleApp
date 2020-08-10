package com.abhishek.sampleapp.ui

import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.abhishek.sampleapp.session.SessionManager
import com.abhishek.sampleapp.ui.ResponseType.Dialog
import com.abhishek.sampleapp.ui.ResponseType.None
import com.abhishek.sampleapp.ui.ResponseType.Toast
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Abhishek Kumar on 28/07/20.
 * (c)2020 VMock. All rights reserved.
 */

abstract class BaseActivity : DaggerAppCompatActivity(), DataStateChangeListener, UICommunicationListener {

    val TAG: String = "AppDebug"

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onUIMessageReceived(uiMessage: UIMessage) {
        when(uiMessage.uiMessageType){

            is UIMessageType.AreYouSureDialog -> {
                areYouSureDialog(
                    uiMessage.message,
                    uiMessage.uiMessageType.callback
                )
            }

            is UIMessageType.Toast -> {
                displayToast(uiMessage.message)
            }

            is UIMessageType.Dialog -> {
                displayInfoDialog(uiMessage.message)
            }

            is UIMessageType.None -> {
                Log.i(TAG, "onUIMessageReceived: ${uiMessage.message}")
            }
        }
    }

    override fun onDataStateChange(dataState: DataState<*>) {

        dataState?.let {
            GlobalScope.launch(Main) {
                displayProgressBar(it.loading.isLoading)

                it.error?.let { errorEvent ->
                    handleStateError(errorEvent)
                }

                it.data?.let {
                    it.response?.let { responseEvent ->
                        handleStateResponse(responseEvent)
                    }
                }
            }
        }
    }

    private fun handleStateResponse(error: Event<Response>) {
        error.getContentIfNotHandled()?.let {
            when (it.responseType) {
                is Toast -> {
                    it.message?.let { message ->
                        displayToast(message)
                    }
                }
                is Dialog -> {
                    it.message?.let { message ->
                        displaySuccessDialog(message)
                    }
                }
                is None -> {
                    Log.e(TAG, "handleStateError : ${it.message}")
                }
            }
        }
    }

    private fun handleStateError(errorEvent: Event<StateError>) {
        errorEvent.getContentIfNotHandled()?.let {
            when (it.response.responseType) {
                is Toast -> {
                    it.response.message?.let { message ->
                        displayToast(message)
                    }
                }
                is Dialog -> {
                    it.response.message?.let { message ->
                        displayErrorDialog(message)
                    }
                }
                is None -> {
                    Log.e(TAG, "handleStateError : ${it.response.message}")
                }
            }
        }
    }

    abstract fun displayProgressBar(bool: Boolean)

    override fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.applicationWindowToken, 0)
        }
    }
}
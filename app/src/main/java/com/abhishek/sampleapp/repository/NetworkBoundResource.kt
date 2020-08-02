package com.abhishek.sampleapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.abhishek.sampleapp.ui.DataState
import com.abhishek.sampleapp.ui.Response
import com.abhishek.sampleapp.ui.ResponseType
import com.abhishek.sampleapp.util.ApiEmptyResponse
import com.abhishek.sampleapp.util.ApiErrorResponse
import com.abhishek.sampleapp.util.ApiSuccessResponse
import com.abhishek.sampleapp.util.Constants.Companion.NETWORK_TIMEOUT
import com.abhishek.sampleapp.util.Constants.Companion.TESTING_CACHE_DELAY
import com.abhishek.sampleapp.util.Constants.Companion.TESTING_NETWORK_DELAY
import com.abhishek.sampleapp.util.ErrorHandling
import com.abhishek.sampleapp.util.ErrorHandling.Companion.ERROR_CHECK_NETWORK_CONNECTION
import com.abhishek.sampleapp.util.ErrorHandling.Companion.ERROR_UNKNOWN
import com.abhishek.sampleapp.util.ErrorHandling.Companion.UNABLE_TODO_OPERATION_WO_INTERNET
import com.abhishek.sampleapp.util.ErrorHandling.Companion.UNABLE_TO_RESOLVE_HOST
import com.abhishek.sampleapp.util.GenericApiResponse
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Abhishek Kumar on 30/07/20.
 * (c)2020 VMock. All rights reserved.
 */

abstract class NetworkBoundResource<ResponseObject, ViewStateType>
    (
    isNetworkAvailable: Boolean, //is there a network connection?
    isNetworkRequest: Boolean // is this a network request?
) {

    private val TAG: String = "AppDebug"

    protected val result = MediatorLiveData<DataState<ViewStateType>>()
    protected lateinit var job: CompletableJob
    protected lateinit var coroutineScope: CoroutineScope

    init {
        setJob(initNewJob())
        setValue(DataState.loading(isLoading = true, cacheData = null))

        if (isNetworkRequest) {
            if (isNetworkAvailable) {
                coroutineScope.launch {

                    // simulate a network delay for testing
                    delay(TESTING_NETWORK_DELAY)

                    withContext(Dispatchers.Main) {

                        // make network call
                        val apiResponse = createCall()
                        result.addSource(apiResponse) { response ->
                            result.removeSource(apiResponse)

                            coroutineScope.launch {
                                handleNetworkCall(response)
                            }
                        }
                    }
                }

                GlobalScope.launch(Dispatchers.IO) {
                    delay(NETWORK_TIMEOUT)

                    if (!job.isCompleted) {
                        Log.e(TAG, "NetworkBoundResource: JOB NETWORK TIMEOUT.")
                        job.cancel(CancellationException(UNABLE_TO_RESOLVE_HOST))
                    }
                }
            } else {
                onErrorReturn(UNABLE_TODO_OPERATION_WO_INTERNET, shouldUseDialog = true, shouldUseToast = false)
            }
        } else {
            coroutineScope.launch {
                // fake delay for testing cache
                delay(TESTING_CACHE_DELAY)

                // view data cache only annd return
                createCacheRequestAndReturn()
            }
        }
    }

    suspend fun handleNetworkCall(response: GenericApiResponse<ResponseObject>?) {
        when (response) {
            is ApiSuccessResponse -> {
                handleApiSuccessResponse(response)
            }
            is ApiErrorResponse -> {
                Log.e(TAG, "NetworkBoundResource: ${response.errorMessage}")
                onErrorReturn(response.errorMessage, true, false)
            }
            is ApiEmptyResponse -> {
                Log.e(TAG, "NetworkBoundResource: Request returned NOTHING (HTTP 204)")
                onErrorReturn("HTTP 204. Returned nothing.", true, false)
            }
        }
    }

    fun onCompleteJob(dataState: DataState<ViewStateType>) {
        GlobalScope.launch(Dispatchers.Main) {
            job.complete()
            setValue(dataState)
        }
    }

    private fun setValue(dataState: DataState<ViewStateType>) {
        result.value = dataState
    }

    fun onErrorReturn(errorMessage: String?, shouldUseDialog: Boolean, shouldUseToast: Boolean) {
        var msg = errorMessage
        var useDialog = shouldUseDialog
        var responseType: ResponseType = ResponseType.None()
        if (msg == null) {
            msg = ERROR_UNKNOWN
        } else if (ErrorHandling.isNetworkError(msg)) {
            msg = ERROR_CHECK_NETWORK_CONNECTION
            useDialog = false
        }
        if (shouldUseToast) {
            responseType = ResponseType.Toast()
        }
        if (useDialog) {
            responseType = ResponseType.Dialog()
        }

        onCompleteJob(
            DataState.error(
                response = Response(
                    message = msg,
                    responseType = responseType
                )
            )
        )
    }

    @OptIn(InternalCoroutinesApi::class)
    private fun initNewJob(): Job {
        Log.d(TAG, "initNewJob: called...")
        job = Job()
        job.invokeOnCompletion(onCancelling = true, invokeImmediately = true, handler = object : CompletionHandler {

            override fun invoke(cause: Throwable?) {
                if (job.isCancelled) {
                    Log.e(TAG, "NetworkBoundResource: Job has been cancelled.")
                    cause?.let {
                        onErrorReturn(it.message, false, true)
                    } ?: onErrorReturn(ERROR_UNKNOWN, false, true)
                } else if (job.isCompleted) {
                    Log.e(TAG, "NetworkBoundResource: Job has been completed...")
                    // Do nothing. Should be handled already.
                }
            }
        })
        coroutineScope = CoroutineScope(Dispatchers.IO + job)
        return job
    }

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>


    abstract suspend fun createCacheRequestAndReturn()

    abstract suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    abstract fun setJob(job: Job)
}
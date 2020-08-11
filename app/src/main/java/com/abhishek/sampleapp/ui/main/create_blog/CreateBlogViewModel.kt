package com.abhishek.sampleapp.ui.main.create_blog

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.abhishek.sampleapp.repository.main.CreateBlogRepository
import com.abhishek.sampleapp.session.SessionManager
import com.abhishek.sampleapp.ui.BaseViewModel
import com.abhishek.sampleapp.ui.DataState
import com.abhishek.sampleapp.ui.Loading
import com.abhishek.sampleapp.ui.main.create_blog.state.CreateBlogStateEvent
import com.abhishek.sampleapp.ui.main.create_blog.state.CreateBlogStateEvent.CreateNewBlogEvent
import com.abhishek.sampleapp.ui.main.create_blog.state.CreateBlogStateEvent.None
import com.abhishek.sampleapp.ui.main.create_blog.state.CreateBlogViewState
import com.abhishek.sampleapp.ui.main.create_blog.state.CreateBlogViewState.NewBlogFields
import com.abhishek.sampleapp.util.AbsentLiveData
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.inject.Inject

/**
 * Created by Abhishek Kumar on 11/08/20.
 * (c)2020 VMock. All rights reserved.
 */

class CreateBlogViewModel
@Inject
constructor(
    val createBlogRepository: CreateBlogRepository,
    val sessionManager: SessionManager
) : BaseViewModel<CreateBlogStateEvent, CreateBlogViewState>() {

    override fun handleStateEvent(stateEvent: CreateBlogStateEvent): LiveData<DataState<CreateBlogViewState>> {
        when (stateEvent) {

            is CreateNewBlogEvent -> {
                return sessionManager.cachedToken.value?.let { authToken ->

                    val title = RequestBody.create(MediaType.parse("text/plain"), stateEvent.title)
                    val body = RequestBody.create(MediaType.parse("text/plain"), stateEvent.body)

                    createBlogRepository.createNewBlogPost(
                        authToken,
                        title,
                        body,
                        stateEvent.image
                    )
                } ?: AbsentLiveData.create()
            }

            is None -> {
                return liveData {
                    emit(
                        DataState(
                            null,
                            Loading(false),
                            null
                        )
                    )
                }
            }
        }
    }

    override fun initNewViewState(): CreateBlogViewState {
        return CreateBlogViewState()
    }

    fun setNewBlogFields(title: String?, body: String?, uri: Uri?) {
        val update = getCurrentViewStateOrNew()
        val newBlogFields = update.blogFields
        title?.let { newBlogFields.newBlogTitle = it }
        body?.let { newBlogFields.newBlogBody = it }
        uri?.let { newBlogFields.newImageUri = it }
        update.blogFields = newBlogFields
        _viewState.value = update
    }

    fun clearNewBlogFields() {
        val update = getCurrentViewStateOrNew()
        update.blogFields = NewBlogFields()
        setViewState(update)
    }

    fun getNewImageUri(): Uri? {
        getCurrentViewStateOrNew().let {
            it.blogFields.let {
                return it.newImageUri
            }
        }
    }

    fun cancelActiveJobs() {
        createBlogRepository.cancelActiveJobs()
        handlePendingData()
    }

    fun handlePendingData() {
        setStateEvent(None())
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}
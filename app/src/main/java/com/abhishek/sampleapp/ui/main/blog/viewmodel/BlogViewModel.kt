package com.abhishek.sampleapp.ui.main.blog.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.abhishek.sampleapp.repository.main.BlogRepository
import com.abhishek.sampleapp.session.SessionManager
import com.abhishek.sampleapp.ui.BaseViewModel
import com.abhishek.sampleapp.ui.DataState
import com.abhishek.sampleapp.ui.Loading
import com.abhishek.sampleapp.ui.main.blog.state.BlogStateEvent
import com.abhishek.sampleapp.ui.main.blog.state.BlogStateEvent.BlogSearchEvent
import com.abhishek.sampleapp.ui.main.blog.state.BlogStateEvent.CheckAuthorOfBlogPost
import com.abhishek.sampleapp.ui.main.blog.state.BlogStateEvent.None
import com.abhishek.sampleapp.ui.main.blog.state.BlogViewState
import com.abhishek.sampleapp.util.AbsentLiveData
import com.bumptech.glide.RequestManager
import javax.inject.Inject

/**
 * Created by Abhishek Kumar on 08/08/20.
 * (c)2020 VMock. All rights reserved.
 */

class BlogViewModel
@Inject
constructor(
    private val sessionManager: SessionManager,
    private val blogRepository: BlogRepository,
    private val sharedPreferences: SharedPreferences,
    private val requestManager: RequestManager
) : BaseViewModel<BlogStateEvent, BlogViewState>() {

    override fun handleStateEvent(stateEvent: BlogStateEvent): LiveData<DataState<BlogViewState>> {
        when (stateEvent) {

            is BlogSearchEvent -> {
                return sessionManager.cachedToken.value?.let { authToken ->
                    blogRepository.searchBlogPosts(
                        authToken,
                        getSearchQuery(),
                        getPage()
                    )
                } ?: AbsentLiveData.create()
            }

            is CheckAuthorOfBlogPost -> {
                return AbsentLiveData.create()
            }

            is None -> {
                return object : LiveData<DataState<BlogViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        value = DataState(null, Loading(false), null)
                    }
                }
            }
        }
    }

    override fun initNewViewState(): BlogViewState {
        return BlogViewState()
    }

    fun cancelActiveJobs() {
        blogRepository.cancelActiveJobs() // cancel active jobs
        handlePendingData() // hide progress bar
    }

    fun handlePendingData() {
        setStateEvent(None())
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}
package com.abhishek.sampleapp.ui.main.blog

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.abhishek.sampleapp.models.BlogPost
import com.abhishek.sampleapp.repository.main.BlogRepository
import com.abhishek.sampleapp.session.SessionManager
import com.abhishek.sampleapp.ui.BaseViewModel
import com.abhishek.sampleapp.ui.DataState
import com.abhishek.sampleapp.ui.main.blog.state.BlogStateEvent
import com.abhishek.sampleapp.ui.main.blog.state.BlogStateEvent.BlogSearchEvent
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
                return AbsentLiveData.create()
            }
            is None -> {
                return AbsentLiveData.create()
            }
        }
    }

    override fun initNewViewState(): BlogViewState {
        return BlogViewState()
    }

    fun setQuery(query: String) {
        val update = getCurrentViewStateOrNew()
        if (query.equals(update.blogFiels.searchQuery)) {
            return
        }
        update.blogFiels.searchQuery = query
        _viewState.value = update
    }

    fun setBlogListData(blogList: List<BlogPost>) {
        val update = getCurrentViewStateOrNew()
        update.blogFiels.blogList = blogList
        _viewState.value = update
    }

    fun cancelActiveJobs() {
        blogRepository.cancelActiveJobs()
        handlePendingData()
    }

    private fun handlePendingData() {
        setStateEvent(None())
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}
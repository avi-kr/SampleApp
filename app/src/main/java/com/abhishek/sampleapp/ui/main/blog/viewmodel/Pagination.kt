package com.abhishek.sampleapp.ui.main.blog.viewmodel

import android.util.Log
import com.abhishek.sampleapp.ui.main.blog.state.BlogStateEvent.BlogSearchEvent
import com.abhishek.sampleapp.ui.main.blog.state.BlogViewState

/**
 * Created by Abhishek Kumar on 09/08/20.
 * (c)2020 VMock. All rights reserved.
 */

fun BlogViewModel.resetPage() {
    val update = getCurrentViewStateOrNew()
    update.blogFields.page = 1
    setViewState(update)
}

fun BlogViewModel.loadFirstPage() {
    setQueryInProgress(true)
    setQueryExhausted(false)
    resetPage()
    setStateEvent(BlogSearchEvent())
    Log.e(TAG, "BlogViewModel: loadFirstPage: ${getSearchQuery()}")
}

private fun BlogViewModel.incrementPageNumber() {
    val update = getCurrentViewStateOrNew()
    val page = update.copy().blogFields.page // get current page
    update.blogFields.page = page + 1
    setViewState(update)
}

fun BlogViewModel.nextPage() {
    if (!getIsQueryInProgress()
        && !getIsQueryExhausted()
    ) {
        Log.d(TAG, "BlogViewModel: Attempting to load next page...")
        incrementPageNumber()
        setQueryInProgress(true)
        setStateEvent(BlogSearchEvent())
    }
}

fun BlogViewModel.handleIncomingBlogListData(viewState: BlogViewState) {
    Log.d(TAG, "BlogViewModel, DataState: ${viewState}")
    Log.d(
        TAG, "BlogViewModel, DataState: isQueryInProgress?: " +
                "${viewState.blogFields.isQueryInProgress}"
    )
    Log.d(
        TAG, "BlogViewModel, DataState: isQueryExhausted?: " +
                "${viewState.blogFields.isQueryExhausted}"
    )
    setQueryInProgress(viewState.blogFields.isQueryInProgress)
    setQueryExhausted(viewState.blogFields.isQueryExhausted)
    setBlogListData(viewState.blogFields.blogList)
}
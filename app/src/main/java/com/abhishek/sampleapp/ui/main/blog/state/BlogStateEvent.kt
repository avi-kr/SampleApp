package com.abhishek.sampleapp.ui.main.blog.state

import okhttp3.MultipartBody

/**
 * Created by Abhishek Kumar on 08/08/20.
 * (c)2020 VMock. All rights reserved.
 */

sealed class BlogStateEvent {

    class BlogSearchEvent : BlogStateEvent()

    class CheckAuthorOfBlogPost : BlogStateEvent()

    class DeleteBlogPostEvent : BlogStateEvent()

    data class UpdateBlogPostEvent(
        val title: String,
        val body: String,
        val image: MultipartBody.Part?
    ): BlogStateEvent()

    class None : BlogStateEvent()
}
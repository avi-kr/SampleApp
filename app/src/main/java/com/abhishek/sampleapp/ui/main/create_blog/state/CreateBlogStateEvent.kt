package com.abhishek.sampleapp.ui.main.create_blog.state

import okhttp3.MultipartBody

/**
 * Created by Abhishek Kumar on 11/08/20.
 * (c)2020 VMock. All rights reserved.
 */

sealed class CreateBlogStateEvent {

    data class CreateNewBlogEvent(
        val title: String,
        val body: String,
        val image: MultipartBody.Part
    ) : CreateBlogStateEvent()

    class None : CreateBlogStateEvent()
}
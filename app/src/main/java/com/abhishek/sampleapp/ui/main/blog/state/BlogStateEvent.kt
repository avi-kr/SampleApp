package com.abhishek.sampleapp.ui.main.blog.state

/**
 * Created by Abhishek Kumar on 08/08/20.
 * (c)2020 VMock. All rights reserved.
 */

sealed class BlogStateEvent {

    class BlogSearchEvent : BlogStateEvent()

    class CheckAuthorOfBlogPost : BlogStateEvent()

    class None : BlogStateEvent()
}
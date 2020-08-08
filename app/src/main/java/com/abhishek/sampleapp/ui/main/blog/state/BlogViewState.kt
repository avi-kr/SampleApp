package com.abhishek.sampleapp.ui.main.blog.state

import com.abhishek.sampleapp.models.BlogPost

/**
 * Created by Abhishek Kumar on 08/08/20.
 * (c)2020 VMock. All rights reserved.
 */

data class BlogViewState(
    // BlogFragments vars
    var blogFields: BlogFields = BlogFields()

    // View Blog Fragments vars

    // Update Blog Fragments vars
) {

    data class BlogFields(
        var blogList: List<BlogPost> = ArrayList<BlogPost>(),
        var searchQuery: String = ""
    )
}
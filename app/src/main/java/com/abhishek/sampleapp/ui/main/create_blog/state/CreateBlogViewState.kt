package com.abhishek.sampleapp.ui.main.create_blog.state

import android.net.Uri

/**
 * Created by Abhishek Kumar on 11/08/20.
 * (c)2020 VMock. All rights reserved.
 */

data class CreateBlogViewState(
    // CreateBlogFragment vars
    var blogFields: NewBlogFields = NewBlogFields()
) {

    data class NewBlogFields(
        var newBlogTitle: String? = null,
        var newBlogBody: String? = null,
        var newImageUri: Uri? = null
    )
}
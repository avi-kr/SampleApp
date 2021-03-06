package com.abhishek.sampleapp.ui.main.blog.state

import android.net.Uri
import android.os.Parcelable
import com.abhishek.sampleapp.models.BlogPost
import com.abhishek.sampleapp.persistence.BlogQueryUtils.Companion.BLOG_ORDER_ASC
import com.abhishek.sampleapp.persistence.BlogQueryUtils.Companion.ORDER_BY_ASC_DATE_UPDATED
import kotlinx.android.parcel.Parcelize

/**
 * Created by Abhishek Kumar on 08/08/20.
 * (c)2020 VMock. All rights reserved.
 */

const val BLOG_VIEW_STATE_BUNDLE_KEY = "com.abhishek.sampleapp.ui.main.blog.state.BlogViewState"

@Parcelize
data class BlogViewState(

    // BlogFragment vars
    var blogFields: BlogFields = BlogFields(),

    // ViewBlogFragment vars
    var viewBlogFields: ViewBlogFields = ViewBlogFields(),

    // UpdateBlogFragment vars
    var updatedBlogFields: UpdateBlogFields = UpdateBlogFields()
) : Parcelable {

    @Parcelize
    data class BlogFields(
        var blogList: List<BlogPost> = ArrayList(),
        var searchQuery: String = "",
        var page: Int = 1,
        var isQueryInProgress: Boolean = false,
        var isQueryExhausted: Boolean = false,
        var filter: String = ORDER_BY_ASC_DATE_UPDATED, // date_update
        var order: String = BLOG_ORDER_ASC, // ""
        var layoutManagerState: Parcelable? = null
    ) : Parcelable

    @Parcelize
    data class ViewBlogFields(
        var blogPost: BlogPost? = null,
        var isAuthorOfBlogPost: Boolean = false
    ) : Parcelable

    @Parcelize
    data class UpdateBlogFields(
        var updatedBlogTitle: String? = null,
        var updatedBlogBody: String? = null,
        var updatedImageUri: Uri? = null

    ) : Parcelable
}
package com.abhishek.sampleapp.ui.main.blog

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.abhishek.sampleapp.R
import com.abhishek.sampleapp.R.string
import com.abhishek.sampleapp.di.main.MainScope
import com.abhishek.sampleapp.models.BlogPost
import com.abhishek.sampleapp.ui.AreYouSureCallback
import com.abhishek.sampleapp.ui.UIMessage
import com.abhishek.sampleapp.ui.UIMessageType
import com.abhishek.sampleapp.ui.main.blog.state.BLOG_VIEW_STATE_BUNDLE_KEY
import com.abhishek.sampleapp.ui.main.blog.state.BlogStateEvent.CheckAuthorOfBlogPost
import com.abhishek.sampleapp.ui.main.blog.state.BlogStateEvent.DeleteBlogPostEvent
import com.abhishek.sampleapp.ui.main.blog.state.BlogViewState
import com.abhishek.sampleapp.ui.main.blog.viewmodel.BlogViewModel
import com.abhishek.sampleapp.ui.main.blog.viewmodel.getBlogPost
import com.abhishek.sampleapp.ui.main.blog.viewmodel.isAuthorOfBlogPost
import com.abhishek.sampleapp.ui.main.blog.viewmodel.removeDeletedBlogPost
import com.abhishek.sampleapp.ui.main.blog.viewmodel.setIsAuthorOfBlogPost
import com.abhishek.sampleapp.ui.main.blog.viewmodel.setUpdatedBlogFields
import com.abhishek.sampleapp.util.DateUtils
import com.abhishek.sampleapp.util.SuccessHandling.Companion.SUCCESS_BLOG_DELETED
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.fragment_view_blog.blog_author
import kotlinx.android.synthetic.main.fragment_view_blog.blog_body
import kotlinx.android.synthetic.main.fragment_view_blog.blog_image
import kotlinx.android.synthetic.main.fragment_view_blog.blog_title
import kotlinx.android.synthetic.main.fragment_view_blog.blog_update_date
import kotlinx.android.synthetic.main.fragment_view_blog.delete_button
import javax.inject.Inject

/**
 * Created by Abhishek Kumar on 03/08/20.
 * (c)2020 VMock. All rights reserved.
 */

@MainScope
class ViewBlogFragment
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val requestManager: RequestManager
) : BaseBlogFragment(R.layout.fragment_view_blog) {

    val viewModel: BlogViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cancelActiveJobs()
        // Restore state after process death
        savedInstanceState?.let { inState ->
            (inState[BLOG_VIEW_STATE_BUNDLE_KEY] as BlogViewState?)?.let { viewState ->
                viewModel.setViewState(viewState)
            }
        }
    }

    /**
     * !IMPORTANT!
     * Must save ViewState b/c in event of process death the LiveData in ViewModel will be lost
     */
    override fun onSaveInstanceState(outState: Bundle) {
        val viewState = viewModel.viewState.value

        //clear the list. Don't want to save a large list to bundle.
        viewState?.blogFields?.blogList = ArrayList()

        outState.putParcelable(
            BLOG_VIEW_STATE_BUNDLE_KEY,
            viewState
        )
        super.onSaveInstanceState(outState)
    }

    override fun cancelActiveJobs() {
        viewModel.cancelActiveJobs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        subscribeObservers()
        checkIsAuthorOfBlogPost()
        stateChangeListener.expandAppBar()

        delete_button.setOnClickListener {
            confirmDeleteRequest()
        }
    }

    private fun confirmDeleteRequest() {
        val callback: AreYouSureCallback = object : AreYouSureCallback {

            override fun proceed() {
                deleteBlogPost()
            }

            override fun cancel() {
                // ignore
            }
        }
        uiCommunicationListener.onUIMessageReceived(
            UIMessage(
                getString(string.are_you_sure_delete),
                UIMessageType.AreYouSureDialog(callback)
            )
        )
    }

    fun deleteBlogPost() {
        viewModel.setStateEvent(
            DeleteBlogPostEvent()
        )
    }

    fun checkIsAuthorOfBlogPost() {
        viewModel.setIsAuthorOfBlogPost(false) // reset
        viewModel.setStateEvent(CheckAuthorOfBlogPost())
    }

    fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            if (dataState != null) {
                dataState?.let {
                    stateChangeListener.onDataStateChange(dataState)
                    dataState.data?.let { data ->
                        data.data?.getContentIfNotHandled()?.let { viewState ->
                            viewModel.setIsAuthorOfBlogPost(
                                viewState.viewBlogFields.isAuthorOfBlogPost
                            )
                        }
                        data.response?.peekContent()?.let { response ->
                            if (response.message.equals(SUCCESS_BLOG_DELETED)) {
                                viewModel.removeDeletedBlogPost()
                                findNavController().popBackStack()
                            }
                        }
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.viewBlogFields.blogPost?.let { blogPost ->
                setBlogProperties(blogPost)
            }

            if (viewState.viewBlogFields.isAuthorOfBlogPost) {
                adaptViewToAuthorMode()
            }
        })
    }

    fun adaptViewToAuthorMode() {
        activity?.invalidateOptionsMenu()
        delete_button.visibility = View.VISIBLE
    }

    fun setBlogProperties(blogPost: BlogPost) {
        requestManager
            .load(blogPost.image)
            .into(blog_image)
        blog_title.setText(blogPost.title)
        blog_author.setText(blogPost.username)
        blog_update_date.setText(DateUtils.convertLongToStringDate(blogPost.date_updated))
        blog_body.setText(blogPost.body)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (viewModel.isAuthorOfBlogPost()) {
            inflater.inflate(R.menu.edit_view_menu, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (viewModel.isAuthorOfBlogPost()) {
            when (item.itemId) {
                R.id.edit -> {
                    navUpdateBlogFragment()
                    return true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navUpdateBlogFragment() {
        try {
            // prep for next fragment
            viewModel.setUpdatedBlogFields(
                viewModel.getBlogPost().title,
                viewModel.getBlogPost().body,
                viewModel.getBlogPost().image.toUri()
            )
            findNavController().navigate(R.id.action_viewBlogFragment_to_updateBlogFragment)
        } catch (e: Exception) {
            // send error report or something. These fields should never be null. Not possible
            Log.e(TAG, "Exception: ${e.message}")
        }
    }
}

package com.abhishek.sampleapp.ui.main.blog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.abhishek.sampleapp.R
import com.abhishek.sampleapp.di.Injectable
import com.abhishek.sampleapp.ui.DataStateChangeListener
import com.abhishek.sampleapp.ui.UICommunicationListener
import com.abhishek.sampleapp.ui.main.MainDependencyProvider
import com.abhishek.sampleapp.ui.main.blog.state.BLOG_VIEW_STATE_BUNDLE_KEY
import com.abhishek.sampleapp.ui.main.blog.state.BlogViewState
import com.abhishek.sampleapp.ui.main.blog.viewmodel.BlogViewModel

/**
 * Created by Abhishek Kumar on 03/08/20.
 * (c)2020 VMock. All rights reserved.
 */

abstract class BaseBlogFragment : Fragment(), Injectable {

    val TAG: String = "AppDebug"

    lateinit var dependencyProvider: MainDependencyProvider

    lateinit var stateChangeListener: DataStateChangeListener

    lateinit var uiCommunicationListener: UICommunicationListener

    lateinit var viewModel: BlogViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBarWithNavController(R.id.blogFragment, activity as AppCompatActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(
                this,
                dependencyProvider.getVMProviderFactory()
            ).get(BlogViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        cancelActiveJobs()

        // Restore state after process death
        savedInstanceState?.let { inState ->
            (inState[BLOG_VIEW_STATE_BUNDLE_KEY] as BlogViewState?)?.let { viewState ->
                viewModel.setViewState(viewState)
            }
        }
    }

    fun isViewModelInitialized() = ::viewModel.isInitialized

    /**
     * !IMPORTANT!
     * Must save ViewState b/c in event of process death the LiveData in ViewModel will be lost
     */
    override fun onSaveInstanceState(outState: Bundle) {
        if (isViewModelInitialized()) {
            val viewState = viewModel.viewState.value
            viewState?.blogFields?.blogList = ArrayList()
            outState.putParcelable(
                BLOG_VIEW_STATE_BUNDLE_KEY,
                viewState
            )
        }
        super.onSaveInstanceState(outState)
    }

    fun cancelActiveJobs() {
        viewModel.cancelActiveJobs()
    }

    /**
     * @fragmentId is id of fragment from graph to be EXCLUDED from action back bar nav
     */
    fun setupActionBarWithNavController(fragmentId: Int, activity: AppCompatActivity) {
        val appBarConfiguration = AppBarConfiguration(setOf(fragmentId))
        NavigationUI.setupActionBarWithNavController(
            activity,
            findNavController(),
            appBarConfiguration
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            stateChangeListener = context as DataStateChangeListener
        } catch (e: ClassCastException) {
            Log.e(TAG, "$context must implement DataStateChangeListener")
        }
        try {
            uiCommunicationListener = context as UICommunicationListener
        } catch (e: ClassCastException) {
            Log.e(TAG, "$context must implement UICommunicationListener")
        }
        try {
            dependencyProvider = context as MainDependencyProvider
        } catch (e: ClassCastException) {
            Log.e(TAG, "$context must implement MainDependencyProvider")
        }
    }
}
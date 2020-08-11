package com.abhishek.sampleapp.ui.main

import com.abhishek.sampleapp.viewmodels.ViewModelProviderFactory
import com.bumptech.glide.RequestManager

/**
 * Created by Abhishek Kumar on 11/08/20.
 * (c)2020 VMock. All rights reserved.
 *
 * Provides app-level dependencies to various BaseFragments:
 * 1) BaseBlogFragment
 * 2) BaseCreateBlogFragment
 * 3) BaseAccountFragment
 *
 * Must do this because of process death issue and restoring state.
 * Why?
 * Can't set values that were saved in instance state to ViewModel because Viewmodel
 * hasn't been created yet when onCreate is called.
 */
interface MainDependencyProvider {

    fun getVMProviderFactory(): ViewModelProviderFactory

    fun getGlideRequestManager(): RequestManager
}
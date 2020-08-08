package com.abhishek.sampleapp.di.main

import androidx.lifecycle.ViewModel
import com.abhishek.sampleapp.di.ViewModelKey
import com.abhishek.sampleapp.ui.main.account.AccountViewModel
import com.abhishek.sampleapp.ui.main.blog.BlogViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Abhishek Kumar on 03/08/20.
 * (c)2020 VMock. All rights reserved.
 */
@Module
abstract class MainViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(accountViewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BlogViewModel::class)
    abstract fun bindBlogViewModel(blogViewModel: BlogViewModel): ViewModel
}
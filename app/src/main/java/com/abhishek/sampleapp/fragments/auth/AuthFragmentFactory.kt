package com.abhishek.sampleapp.fragments.auth

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.abhishek.sampleapp.di.auth.AuthScope
import com.abhishek.sampleapp.ui.auth.ForgotPasswordFragment
import com.abhishek.sampleapp.ui.auth.LauncherFragment
import com.abhishek.sampleapp.ui.auth.LoginFragment
import com.abhishek.sampleapp.ui.auth.RegisterFragment
import javax.inject.Inject

/**
 * Created by Abhishek Kumar on 30/08/20.
 * (c)2020 VMock. All rights reserved.
 */

@AuthScope
class AuthFragmentFactory
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when (className) {

            LauncherFragment::class.java.name -> {
                LauncherFragment(viewModelFactory)
            }

            LoginFragment::class.java.name -> {
                LoginFragment(viewModelFactory)
            }

            RegisterFragment::class.java.name -> {
                RegisterFragment(viewModelFactory)
            }

            ForgotPasswordFragment::class.java.name -> {
                ForgotPasswordFragment(viewModelFactory)
            }

            else -> {
                LauncherFragment(viewModelFactory)
            }
        }


}
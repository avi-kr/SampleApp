package com.abhishek.sampleapp.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.abhishek.sampleapp.R
import kotlinx.android.synthetic.main.fragment_launcher.focusable_view
import kotlinx.android.synthetic.main.fragment_launcher.forgot_password
import kotlinx.android.synthetic.main.fragment_launcher.login
import kotlinx.android.synthetic.main.fragment_launcher.register

class LauncherFragment : BaseAuthFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_launcher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        register.setOnClickListener {
            navRegistration()
        }

        login.setOnClickListener {
            navLogin()
        }

        forgot_password.setOnClickListener {
            navForgotPassword()
        }

        focusable_view.requestFocus() // reset focus

        Log.d(TAG, "LauncherFragment: ${viewModel}")
    }

    fun navLogin() {
        findNavController().navigate(R.id.action_launcherFragment_to_loginFragment)
    }

    fun navRegistration() {
        findNavController().navigate(R.id.action_launcherFragment_to_registerFragment)
    }

    fun navForgotPassword() {
        findNavController().navigate(R.id.action_launcherFragment_to_forgotPasswordFragment)
    }
}
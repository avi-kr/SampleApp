package com.abhishek.sampleapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.abhishek.sampleapp.R
import com.abhishek.sampleapp.ui.auth.state.AuthStateEvent.RegisterAttemptEvent
import com.abhishek.sampleapp.ui.auth.state.RegistrationFields
import kotlinx.android.synthetic.main.fragment_register.input_email
import kotlinx.android.synthetic.main.fragment_register.input_password
import kotlinx.android.synthetic.main.fragment_register.input_password_confirm
import kotlinx.android.synthetic.main.fragment_register.input_username
import kotlinx.android.synthetic.main.fragment_register.register_button

class RegisterFragment : BaseAuthFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        register_button.setOnClickListener {
            register()
        }

        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            it.registrationFields?.let { registrationFields ->
                registrationFields.registration_email?.let { input_email.setText(it) }
                registrationFields.registration_username?.let { input_username.setText(it) }
                registrationFields.registration_password?.let { input_password.setText(it) }
                registrationFields.registration_confirm_password?.let { input_password_confirm.setText(it) }
            }
        })
    }

    fun register() {
        viewModel.setStateEvent(
            RegisterAttemptEvent(
                input_email.text.toString(),
                input_username.text.toString(),
                input_password.text.toString(),
                input_password_confirm.text.toString()
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setRegistrationnnFields(
            RegistrationFields(
                input_email.text.toString(),
                input_username.text.toString(),
                input_password.text.toString(),
                input_password_confirm.text.toString()
            )
        )
    }
}
package com.abhishek.sampleapp.ui.main.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.abhishek.sampleapp.R
import com.abhishek.sampleapp.models.AccountProperties
import com.abhishek.sampleapp.ui.main.account.state.AccountStateEvent
import kotlinx.android.synthetic.main.fragment_account.change_password
import kotlinx.android.synthetic.main.fragment_account.email
import kotlinx.android.synthetic.main.fragment_account.logout_button
import kotlinx.android.synthetic.main.fragment_account.username

/**
 * Created by Abhishek Kumar on 03/08/20.
 * (c)2020 VMock. All rights reserved.
 */

class AccountFragment : BaseAccountFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        change_password.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_changePasswordFragment)
        }

        logout_button.setOnClickListener {
            viewModel.logout()
        }
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            dataState?.let {
                stateChangeListener.onDataStateChange(dataState)
                it.data?.let { data ->
                    data.data?.let { event ->
                        event.getContentIfNotHandled()?.let { viewState ->
                            viewState.accountProperties?.let { accountProperties ->
                                Log.d(TAG, "AccountFragment, DataState: ${accountProperties}")
                                viewModel.setAccountPropertiesData(accountProperties)
                            }
                        }
                    }
                }
            }
        })
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState?.let {
                viewState.accountProperties?.let {
                    Log.d(TAG, "AccountFragment: ViewState: ${it}")
                    setAccountDataFields(it)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.setStateEvent(
            AccountStateEvent.GetAccountPropertiesEvent()
        )
    }

    private fun setAccountDataFields(accountProperties: AccountProperties) {
        email?.setText(accountProperties.email)
        username?.setText(accountProperties.username)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_view_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> {
                findNavController().navigate(R.id.action_accountFragment_to_updateAccountFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
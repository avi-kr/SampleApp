package com.abhishek.sampleapp.ui.main.account.state

import android.os.Parcelable
import com.abhishek.sampleapp.models.AccountProperties
import kotlinx.android.parcel.Parcelize

/**
 * Created by Abhishek Kumar on 06/08/20.
 * (c)2020 VMock. All rights reserved.
 */

const val ACCOUNT_VIEW_STATE_BUNDLE_KEY = "com.abhishek.sampleapp.ui.main.account.state.AccountViewState"

@Parcelize
class AccountViewState(
    var accountProperties: AccountProperties? = null
) : Parcelable
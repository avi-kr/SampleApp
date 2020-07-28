package com.abhishek.paidcourseapp.ui.auth

import androidx.lifecycle.ViewModel
import com.abhishek.paidcourseapp.repository.AuthRepository
import javax.inject.Inject

/**
 * Created by Abhishek Kumar on 27/07/20.
 * (c)2020 VMock. All rights reserved.
 */

class AuthViewModel
@Inject
constructor(
    val authRepository: AuthRepository
) : ViewModel() {

}
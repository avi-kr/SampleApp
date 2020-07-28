package com.abhishek.paidcourseapp.util

import androidx.lifecycle.LiveData

/**
 * Created by Abhishek Kumar on 28/07/20.
 * (c)2020 VMock. All rights reserved.
 */

class AbsentLiveData<T : Any?> private constructor() : LiveData<T>() {

    init {

        postValue(null)
    }

    companion object {
        fun <T> create(): LiveData<T> {
            return AbsentLiveData()
        }
    }
}
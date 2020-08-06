package com.abhishek.sampleapp.api.main

import androidx.lifecycle.LiveData
import com.abhishek.sampleapp.models.AccountProperties
import com.abhishek.sampleapp.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * Created by Abhishek Kumar on 06/08/20.
 * (c)2020 VMock. All rights reserved.
 */

interface OpenApiMainService {

    @GET("account/properties")
    fun getAccountProperties(
        @Header("Authorization") authorization: String
    ): LiveData<GenericApiResponse<AccountProperties>>
}
package com.abhishek.sampleapp.api.auth

import androidx.lifecycle.LiveData
import com.abhishek.sampleapp.api.auth.network_responsne.LoginResponse
import com.abhishek.sampleapp.api.auth.network_responsne.RegistrationResponse
import com.abhishek.sampleapp.di.auth.AuthScope
import com.abhishek.sampleapp.util.GenericApiResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Abhishek Kumar on 27/07/20.
 * (c)2020 VMock. All rights reserved.
 */

@AuthScope
interface OpenApiAuthService {

    @POST("account/login")
    @FormUrlEncoded
    fun login(
        @Field("username") email: String,
        @Field("password") password: String
    ): LiveData<GenericApiResponse<LoginResponse>>

    @POST("account/register")
    @FormUrlEncoded
    fun register(
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("password2") password2: String
    ): LiveData<GenericApiResponse<RegistrationResponse>>
}
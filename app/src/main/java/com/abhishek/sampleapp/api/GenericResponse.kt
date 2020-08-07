package com.abhishek.sampleapp.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Abhishek Kumar on 07/08/20.
 * (c)2020 VMock. All rights reserved.
 */

class GenericResponse(

    @SerializedName("response")
    @Expose
    var response: String
)
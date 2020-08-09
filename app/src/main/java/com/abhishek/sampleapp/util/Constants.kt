package com.abhishek.sampleapp.util

/**
 * Created by Abhishek Kumar on 28/07/20.
 * (c)2020 VMock. All rights reserved.
 */

class Constants {

    companion object {

        const val BASE_URL = "https://open-api.xyz/api/"
        const val PASSWORD_RESET_URL: String = "https://open-api.xyz/password_reset/"

        const val NETWORK_TIMEOUT = 6000L
        const val TESTING_NETWORK_DELAY = 0L // fake network delay for testing
        const val TESTING_CACHE_DELAY = 0L // fake cache delay for testing

        const val PAGINATION_PAGE_SIZE = 10
    }
}
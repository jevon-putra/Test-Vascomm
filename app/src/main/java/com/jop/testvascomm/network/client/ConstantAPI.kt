package com.jop.testvascomm.network.client

import com.jop.testvascomm.BuildConfig

object ConstantAPI {
    val BASE_URL = if (BuildConfig.DEBUG) "https://reqres.in/api/" else "https://reqres.in/api/"
    const val HEADER_AUTH_TAG = "Authorization"
    const val HEADER_AUTH_KEY = "Bearer "
}
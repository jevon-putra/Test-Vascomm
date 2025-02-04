package com.jop.testvascomm.network.api

import com.jop.testvascomm.data.model.request.LoginRequest
import com.jop.testvascomm.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}